package com.xzy.user.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.xzy.common.exception.ExceptionCast;
import com.xzy.common.model.code.CommonCode;
import com.xzy.enums.UserEnums;
import com.xzy.pojo.user.HmUser;
import com.xzy.pojo.user.HmUserPrePt;
import com.xzy.pojo.user.code.UcenterCode;
import com.xzy.pojo.user.response.UserActualPt;
import com.xzy.pojo.user.response.UserPrePtRp;
import com.xzy.user.UserPrePtService;
import com.xzy.user.dao.UserMapper;
import com.xzy.user.dao.UserPrePtMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DubboService(interfaceClass = UserPrePtService.class)
@Slf4j
public class UserPrePtImpl implements UserPrePtService {
    @Autowired
    UserPrePtMapper userPrePtMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 按用户id查询预设占成数据
     *
     * @param userId
     * @return
     */
    @Override
    public List<UserPrePtRp> selectUserPreByUserId(Integer userId) {
        ExceptionCast.castCheck(ObjectUtil.isNull(userId), CommonCode.INVALID_PARAM);
        return userPrePtMapper.selectUserPreByUserId(userId);
    }

    /**
     * 按会员ID查询占成
     *
     * @param userId
     * @return
     */
    @Override
    public List<UserPrePtRp> selectUserMemberPreByUserId(Integer userId) {
        ExceptionCast.castCheck(ObjectUtil.isNull(userId), CommonCode.INVALID_PARAM);
        return userPrePtMapper.selectUserMemberPreByUserId(userId);
    }

    /**
     * 保存预设占成
     *
     * @param savePrePts
     * @param parentId
     */
    @Override
    public void savePrePt(List<HmUserPrePt> savePrePts, Integer parentId) {
        //查询出上级数据
        List<UserPrePtRp> userPrePtRps = userPrePtMapper.selectUserPreByUserId(parentId);
        //进入验证
        this.checkPtRange(savePrePts, userPrePtRps);
        userPrePtMapper.insertList(savePrePts);
//        ExceptionCast.cast("失败");
    }

    /**
     * 验证占成是否符合上级范围
     *
     * @param savePrePts
     * @param upperPts
     */
    public void checkPtRange(List<HmUserPrePt> savePrePts, List<UserPrePtRp> upperPts) {
        upperPts.forEach(upperPre -> {
            savePrePts.forEach(savePre -> {
                if (upperPre.getLotteryId().equals(savePre.getLotteryId())) {
                    //预设占成范围在上级最大-最小之间
                    if (savePre.getPtPct() > upperPre.getPtMaxPct() || savePre.getPtPct() < upperPre.getPtMinPct()) {
                        ExceptionCast.cast(UcenterCode.UCENTER_PT_ERROR);
                    }
                    //下级最低大于最高占成
                    if (savePre.getPtMinPct() > savePre.getPtMaxPct()) {
                        ExceptionCast.cast(UcenterCode.UCENTER_LOWER_MIN_PT_EXCEED_MAX);
                    }
                    //下级最低占成不能超出上级最大
                    if (savePre.getPtMinPct() > upperPre.getPtMaxPct() || savePre.getPtMinPct() < 0) {
                        ExceptionCast.cast(UcenterCode.UCENTER_LOWER_MIN_PT_EXCEED);
                    }
                    //下级最高占成不能超出上级最大
                    if (savePre.getPtMaxPct() > upperPre.getPtMaxPct() || savePre.getPtMaxPct() < 0) {
                        ExceptionCast.cast(UcenterCode.UCENTER_LOWER_MAX_PT_EXCEED);
                    }
                }
            });
        });
    }

    public Map<String, Object> selectUserActualPt(Integer level, Integer userId) {
        List<UserActualPt> actualPts = userPrePtMapper.selectUserActualPt(userId);
        Integer actualPrePtNum=0;
       /* for (int i = 0; i < actualPts.size(); i++) {
            UserActualPt actualPt = actualPts.get(i);
            if(actualPt.getActualPrePt()==null){
                actualPt.setPrePct(actualPt.getPtPct());
                actualPt.setPreMaxPct(actualPt.getPtMaxPct());
                actualPt.setPreMinPct(actualPt.getPtMinPct());
            }
        }*/
        Integer lotteryId = 0;
        Integer lowerTotalPt = 0;
        Integer lowerTotalPrePt = 0;
        Map<Integer, List<UserActualPt>> resultMap = new HashMap<>();
        for (int i = 0; i < actualPts.size(); i++) {
            UserActualPt actualPt = actualPts.get(i);
            //如果彩种id不相等了，说明循环到第二个彩种了，需要初始化下面参数
            if(actualPt.getPrePct()==null){
                actualPrePtNum=actualPrePtNum+1;//桶计有多少为空
                actualPt.setPrePct(actualPt.getPtPct());
                actualPt.setActualPrePt(actualPt.getActualPct());
                actualPt.setPreMaxPct(actualPt.getPtMaxPct());
                actualPt.setPreMinPct(actualPt.getPtMinPct());
            }
            if (!lotteryId.equals(actualPt.getLotteryId())) {
                lotteryId = actualPt.getLotteryId();
                lowerTotalPt = 0;
                lowerTotalPrePt = 0;
                List<UserActualPt> actualPts1 = new ArrayList<>();
                //去掉会员数据，会员没有实际占成
                if (actualPt.getUserType().equals(UserEnums.Utype.AGENT.toString())) {
                    actualPts1.add(actualPt);
                }
                resultMap.put(lotteryId, actualPts1);
            } else {
                //彩种id存在，直接加入数组中
                if (actualPt.getUserLevel() >= level)
                    resultMap.get(lotteryId).add(0, actualPt);
            }
            Integer myselfPt = Math.min(actualPt.getPtMaxPct() - lowerTotalPt, actualPt.getActualPct());
            Integer myselfPrePt = Math.min(actualPt.getPreMaxPct() - lowerTotalPrePt, actualPt.getActualPrePt());
//            if(actualPt.getUserType().equals("AGENT") && actualPt.getUserLevel()>2) {
//                if (lowerTotalPt > actualPt.getActualPct()) {
//                    myselfPt = 0;
//                } else {
//                    myselfPt = actualPt.getActualPct() - lowerTotalPt;
//                }
//            }
            lowerTotalPt = lowerTotalPt + myselfPt;
            lowerTotalPrePt = lowerTotalPrePt + myselfPrePt;
            actualPt.setActualPct(myselfPt);
            actualPt.setActualPrePt(myselfPrePt);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("resultMap", resultMap);
        map.put("actualPrePtNum", actualPrePtNum);
        return map;
    }

    /**
     * 修改下线占成
     *
     * @param prePt
     */
    public void updateLowerPt(List<HmUserPrePt> prePt, Integer userId) {
        HmUser hmUser = userMapper.selectByPrimaryKey(userId);

        if (null == hmUser) {
            ExceptionCast.cast(UcenterCode.UCENTER_ACCOUNT_NOTEXISTS);
        }
        //查询上级占成数据
        List<UserPrePtRp> userPrePtRps = userPrePtMapper.selectUserPreByUserId(hmUser.getParentId());
        if (hmUser.getUserLevel().equals(2)) {
            //修改公司占成,需要修改最大最小
            this.getCompanyPt(prePt);
        }
        //验证修改占成范围
        this.checkPtRange(prePt, userPrePtRps);
        for (HmUserPrePt pt : prePt) {//修改
            userPrePtMapper.updateHmUserPrePt(pt);
        }
    }

    /**
     * 修复整线占成
     *
     * @param userId
     */
    public void updateUserPrePt(Integer userId) {
        userMapper.updateUserPrePt(userId);
    }


    public void getCompanyPt(List<HmUserPrePt> prePt) {
        for (HmUserPrePt pt : prePt) {
            pt.setPtMaxPct(100 - pt.getPtPct());
            pt.setPtMinPct(100 - pt.getPtPct());
        }
    }

    /**
     * 修改下线预设占成
     *
     * @param prePt
     */
    public void updateLowerPrePt(List<HmUserPrePt> prePt, Integer userId) {
        HmUser hmUser = userMapper.selectByPrimaryKey(userId);
        if (null == hmUser) {
            ExceptionCast.cast(UcenterCode.UCENTER_ACCOUNT_NOTEXISTS);
        }
        //查询上级占成数据
        List<UserPrePtRp> userPrePtRps = userPrePtMapper.selectUserPreByUserId(hmUser.getParentId());
        if (hmUser.getUserLevel().equals(2)) {
            //修改公司占成,需要修改最大最小
            this.getCompanyPrePt(prePt);
        }
        //验证修改占成范围
        this.checkPrePtRange(prePt, userPrePtRps);
        for (HmUserPrePt pt : prePt) {//修改
            userPrePtMapper.updateHmUserPresPt(pt);//修改预设占成
        }
    }

    public void getCompanyPrePt(List<HmUserPrePt> prePt) {
        for (HmUserPrePt pt : prePt) {
            pt.setPreMaxPct(100 - pt.getPrePct());
            pt.setPreMinPct(100 - pt.getPrePct());
        }
    }

    /**
     * 验证占成是否符合上级范围
     *
     * @param savePrePts
     * @param upperPts
     */
    public void checkPrePtRange(List<HmUserPrePt> savePrePts, List<UserPrePtRp> upperPts) {
        upperPts.forEach(upperPre -> {
            savePrePts.forEach(savePre -> {
                if (upperPre.getLotteryId().equals(savePre.getLotteryId())) {
                    //预设占成范围在上级最大-最小之间
                    Integer preMaxPct=0,preMinPct=0;//上级是否有预值 有取上级预设值 没有取占成值
                    if(upperPre.getPrePct()==null &&  upperPre.getPreMaxPct()==null && upperPre.getPreMinPct()==null ){
                        preMaxPct=upperPre.getPtMaxPct();
                        preMinPct=upperPre.getPtMinPct();
                    }else{
                        preMaxPct=upperPre.getPreMaxPct();
                        preMinPct=upperPre.getPreMinPct();
                    }
                    if (savePre.getPrePct() > preMaxPct || savePre.getPrePct() < preMinPct) {
                        ExceptionCast.cast(UcenterCode.UCENTER_PT_ERROR);
                    }
                    //下级最低大于最高占成
                    if (savePre.getPreMinPct() > savePre.getPreMaxPct()) {
                        ExceptionCast.cast(UcenterCode.UCENTER_LOWER_MIN_PT_EXCEED_MAX);
                    }
                    //下级最低占成不能超出上级最大
                    if (savePre.getPreMinPct() > preMaxPct || savePre.getPreMinPct() < 0) {
                        ExceptionCast.cast(UcenterCode.UCENTER_LOWER_MIN_PT_EXCEED);
                    }
                    //下级最高占成不能超出上级最大
                    if (savePre.getPreMaxPct() > preMaxPct || savePre.getPreMaxPct() < 0) {
                        ExceptionCast.cast(UcenterCode.UCENTER_LOWER_MAX_PT_EXCEED);
                    }
                }
            });
        });
    }

    /**
     * 修复整线 预设占成
     *
     * @param userId
     */
    public void updateUserPresPt(Integer userId) {
        userPrePtMapper.updateUserPresPt(userId);
    }

}
