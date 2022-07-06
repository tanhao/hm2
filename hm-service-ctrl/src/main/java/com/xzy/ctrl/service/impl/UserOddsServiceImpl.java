package com.xzy.ctrl.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xzy.common.exception.ExceptionCast;
import com.xzy.common.model.code.CommonCode;
import com.xzy.common.model.code.CtrlCode;
import com.xzy.ctrl.*;
import com.xzy.ctrl.dao.UserOddsMapper;
import com.xzy.ctrl.dao.UserRegressMapper;
import com.xzy.enums.EUserType;
import com.xzy.pojo.ctrl.CategoryDO;
import com.xzy.pojo.ctrl.GroupDO;
import com.xzy.pojo.ctrl.KindDO;
import com.xzy.pojo.ctrl.QuickSettingDO;
import com.xzy.pojo.ctrl.bo.UserOddsBO;
import com.xzy.pojo.ctrl.bo.UserRegressBO;
import com.xzy.pojo.ctrl.request.CopyORLFRQ;
import com.xzy.pojo.ctrl.request.OddsCloseRQ;
import com.xzy.pojo.ctrl.request.OddsRegressLimitFulloddsRQ;
import com.xzy.pojo.ctrl.request.OddsPlusMinusRQ;
import com.xzy.pojo.ctrl.vo.LotteryVO;
import com.xzy.pojo.ctrl.vo.OddsVO;
import com.xzy.pojo.tio.entity.SendInfo;
import com.xzy.pojo.tio.utils.CodeUtil;
import com.xzy.pojo.user.HmUser;
import com.xzy.pojo.user.response.UserInfo;
import com.xzy.socket.TioSocketService;
import com.xzy.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 补货业务逻辑层
 */
@DubboService
@Slf4j
@SuppressWarnings("all")
public class UserOddsServiceImpl implements UserOddsService {

    @Autowired
    private UserOddsMapper userOddsMapper;
    @Autowired
    private UserRegressMapper userRegressMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @DubboReference
    private GroupService groupService;
    @DubboReference
    private KindService kindService;
    @DubboReference
    private CategoryService categoryService;
    @DubboReference
    private LotteryService lotteryService;
    @DubboReference
    private UserLotteryService userLotteryService;
    @DubboReference
    private QuickSettingService quickSettingService;
    @DubboReference
    private UserService userService;
    @DubboReference
    private TioSocketService tioSocketService;

    @Override
    public OddsVO getOddsVO(Integer userId) {
        List<GroupDO> listGroups = groupService.listGroups();
        Map<Integer, List<KindDO>> mapKinds = kindService.mapKindsOfGroupId();
        Map<Integer, List<CategoryDO>> mapCategorys = categoryService.mapCategorysOfKindId();
        List<LotteryVO> listLotterys = userLotteryService.listLotteryVOs(userId);

        List<QuickSettingDO> quickSetting= quickSettingService.listQuickSettings();

        listGroups.forEach(group -> {
            this.JsonIgnoreGroup(group);
        });
        mapKinds.values().forEach(kinds -> kinds.forEach(kind -> {
            this.JsonIgnoreKind(kind);
        }));
        mapCategorys.values().forEach(categorys -> categorys.forEach(category -> {
            this.JsonIgnoreCategory(category);
        }));

        Map<Integer, List<LotteryVO>> mapLotterys = listLotterys.stream()
                .collect(Collectors.groupingBy(LotteryVO::getGroupId));
        for (List<LotteryVO> item : mapLotterys.values()) {
            item.sort((a1, a2) -> a1.getOrdering().compareTo(a2.getOrdering()));
        }
        mapLotterys.values().forEach(tiems -> tiems.forEach(lottery -> {
            this.JsonIgnoreLottery(lottery);
        }));

        UserInfo user=userService.findBaseById(userId);
        EUserType userType=EUserType.valueOf(user.getUserLevel());
        List<UserOddsBO> listUserOddsBOs=null;
        List<UserRegressBO> listUserRegressBOs=null;
        Boolean hasOrder=null;
        List<HmUser> lowers=null;
        if(EUserType.GROUP.equals(userType)){
            listUserOddsBOs=userOddsMapper.listHoutongOdds(1);
            listUserRegressBOs=userRegressMapper.listHoutongRegress(1);
        }else if(EUserType.COMPANY.equals(userType)){
            listUserOddsBOs=userOddsMapper.listCompanyOdds(userId);
            listUserRegressBOs=userRegressMapper.listCompanyRegress(userId);
            lowers = userService.selectLower(user.getParentId(),null);
        }else if(EUserType.AGENT1.equals(userType)){
            if(user.getDisk()){
                listUserOddsBOs=userOddsMapper.listAgent1OddsStandard(userId);
                listUserRegressBOs=userRegressMapper.listAgent1RegressStandard(userId);
            }else{
                listUserOddsBOs=userOddsMapper.listAgent1OddsUnstandard(userId);
                listUserRegressBOs=userRegressMapper.listAgent1RegressUnstandard(userId,user.getParentId());
            }
            hasOrder=userService.countOrder(userId);
            lowers = userService.selectLower(user.getParentId(),user.getDisk());
        }else{
            listUserOddsBOs=userOddsMapper.listAgent2ToAgent10Odds(userId);
            listUserRegressBOs=userRegressMapper.listAgent2ToAgent10Regress(userId);
        }
        Map<Integer,Map<Integer,UserOddsBO>> mapUserOddsBOs=listUserOddsBOs.stream().collect(Collectors.groupingBy(UserOddsBO::getLotteryId,Collectors.toMap(item->item.getCategoryId(), Function.identity())));
        Map<Integer,Map<Integer,UserRegressBO>> mapUserRegressBOs=listUserRegressBOs.stream().collect(Collectors.groupingBy(UserRegressBO::getLotteryId,Collectors.toMap(item->item.getKindId(),Function.identity())));

        Map<String,Boolean> markets= Arrays.asList(user.getMarketOpen().split(",")).stream().collect(Collectors.toMap(item->item,item->true));

        OddsVO oddsVO=new OddsVO();
        oddsVO.setGroups(listGroups);
        oddsVO.setKinds(mapKinds);
        oddsVO.setCategorys(mapCategorys);
        oddsVO.setLotterys(mapLotterys);
        oddsVO.setOdds(mapUserOddsBOs);
        oddsVO.setRegress(mapUserRegressBOs);
        oddsVO.setMarkets(markets);
        oddsVO.setHasLower(user.getAgentCount()>0?true:false);
        oddsVO.setQuickSetting(quickSetting);
        oddsVO.setHasOrder(hasOrder);
        oddsVO.setLowers(lowers);
        oddsVO.setIsStandard(user.getDisk());
        return oddsVO;
    }

    @Override
    public OddsVO getAgent1ORL(Integer userId) {
       OddsVO oddsVO=getOddsVO(userId);
       UserInfo user=userService.findBaseById(userId);
       if(!user.getDisk()){
           List<UserRegressBO>  listUserRegressBOs=userRegressMapper.listAgent1RegressUnstandard2(userId);
           Map<Integer,Map<Integer,UserRegressBO>> mapUserRegressBOs=listUserRegressBOs.stream().collect(Collectors.groupingBy(UserRegressBO::getLotteryId,Collectors.toMap(item->item.getKindId(),Function.identity())));
           oddsVO.setRegress(mapUserRegressBOs);
       }
       return oddsVO;
    }

    @Override
    public OddsRegressLimitFulloddsRQ updateORCLHoutong(OddsRegressLimitFulloddsRQ orlf) {
        ExceptionCast.castCheck(orlf.getUserId()==null, CtrlCode.USER_ID_NULL);
        ExceptionCast.castCheck(orlf.getLotteryIds()==null||orlf.getLotteryIds()==null, CtrlCode.LOTTERYS_NULL);
        log.info("JSON:"+JSON.toJSONString(orlf));
        orlf.setJson(JSON.toJSONString(orlf));
        userOddsMapper.updateORCLHoutong(orlf);
        return orlf;
    }

    @Override
    public OddsRegressLimitFulloddsRQ updateORCLCompany(OddsRegressLimitFulloddsRQ orlf) {
        ExceptionCast.castCheck(orlf.getUserId()==null, CtrlCode.USER_ID_NULL);
        ExceptionCast.castCheck(orlf.getLotteryIds()==null||orlf.getLotteryIds()==null, CtrlCode.LOTTERYS_NULL);
        log.info("JSON:"+JSON.toJSONString(orlf));
        orlf.setJson(JSON.toJSONString(orlf));
         userOddsMapper.updateORCLCompany(orlf);
        return orlf;
    }

    @Override
    public OddsRegressLimitFulloddsRQ updateCompanyORLF(OddsRegressLimitFulloddsRQ orlf) {
        ExceptionCast.castCheck(orlf.getUserId()==null, CtrlCode.USER_ID_NULL);
        ExceptionCast.castCheck(orlf.getLotteryIds()==null||orlf.getLotteryIds()==null, CtrlCode.LOTTERYS_NULL);
        HmUser user=userService.findById(orlf.getUserId());
        ExceptionCast.castCheck(!EUserType.COMPANY.equals(EUserType.valueOf( user.getUserLevel())), CommonCode.UNAUTHORISE);
        ExceptionCast.castCheck(!user.getParentId().equals(orlf.getLoginUserId()), CommonCode.UNAUTHORISE);
        log.info("JSON:"+JSON.toJSONString(orlf));
        orlf.setJson(JSON.toJSONString(orlf));
        userOddsMapper.updateCompanyORLF(orlf);
        return orlf;
    }

    @Override
    public OddsRegressLimitFulloddsRQ updateAgent1ORL(OddsRegressLimitFulloddsRQ orlf) {
        ExceptionCast.castCheck(orlf.getUserId()==null, CtrlCode.USER_ID_NULL);
        ExceptionCast.castCheck(orlf.getLotteryIds()==null||orlf.getLotteryIds()==null, CtrlCode.LOTTERYS_NULL);
        HmUser user=userService.findById(orlf.getUserId());
        ExceptionCast.castCheck(!EUserType.AGENT1.equals(EUserType.valueOf( user.getUserLevel())), CommonCode.UNAUTHORISE);
        ExceptionCast.castCheck(!user.getParentId().equals(orlf.getLoginUserId()), CommonCode.UNAUTHORISE);
        log.info("JSON:"+JSON.toJSONString(orlf));
        orlf.setJson(JSON.toJSONString(orlf));
        userOddsMapper.updateAgent1ORL(orlf);
        if(orlf.getErrcode()==200){
            String[] markets=new String[]{"A","B","C","D"};
            HashMap<String,JSONArray> mapJson=new HashMap<>();
            JSONArray lotterys= JSON.parseArray(orlf.getErrmsg());
            for (Iterator<Object> iterator = lotterys.iterator(); iterator.hasNext(); ) {
                JSONObject lottery = (JSONObject) iterator.next();
                String lotteryId=lottery.getString("lotteryId");
                for(int i=0;i<markets.length;i++){
                    String market=markets[i];
                    JSONArray jsonArray=new JSONArray();
                    JSONArray categorys=lottery.getJSONArray("categorys");
                    for (Iterator<Object> iterator2 = categorys.iterator(); iterator2.hasNext(); ) {
                        JSONObject category = (JSONObject) iterator2.next();
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("categoryId", category.getString("categoryId"));
                        jsonObject.put("odds", category.getDoubleValue("odds"+market.toUpperCase()));
                        jsonArray.add(jsonObject);
                    }
                    String key= StrUtil.format("{}-{}",lotteryId,market);
                    mapJson.put(key,jsonArray);
                }
            }
            Integer userId=user.getUserId();
            Integer userLevel=user.getUserLevel();
            orlf.getLotteryIds().forEach(lotteryId->{
                for(int i=0;i<markets.length;i++) {
                    String market = markets[i];
                    String groupName= StrUtil.format("{}-{}-{}",lotteryId,userId,market);
                    JSONObject retjson = new JSONObject();
                    retjson.put("userId", userId);
                    retjson.put("userLevel", userLevel);
                    String key= StrUtil.format("{}-{}",lotteryId,market);
                    retjson.put("oddss",mapJson.get(key));
                    SendInfo sendInfo = new SendInfo("odds", retjson);
                    try{
                        tioSocketService.sendToGroup(groupName, sendInfo);
                        log.info("[ {} ] [ odds ] : {}", groupName, sendInfo.getContent());
                    }catch (Exception e){
                        log.error("[ {} ] [ odds ] : {}", groupName, sendInfo.getContent());
                    }
                }
            });
        }
        return orlf;
    }


    @Override
    public OddsRegressLimitFulloddsRQ updateAgent1RegressLimit(OddsRegressLimitFulloddsRQ orlf){
        log.info("JSON:"+JSON.toJSONString(orlf));
        orlf.setJson(JSON.toJSONString(orlf));
        userOddsMapper.updateAgent1RegressLimit(orlf);
        return orlf;
    }
    @Override
    public CopyORLFRQ copyCompany(CopyORLFRQ copy) {
        if(copy.getToUserId()==null||copy.getFromUserId()==null){
            ExceptionCast.cast(CtrlCode.USER_ID_NULL);
        }
        userOddsMapper.copyCompany(copy);
        return copy;
    }

    @Override
    public CopyORLFRQ copyAgent1(CopyORLFRQ copy) {
        if(copy.getToUserId()==null||copy.getFromUserId()==null){
            ExceptionCast.cast(CtrlCode.USER_ID_NULL);
        }
        userOddsMapper.copyAgent1(copy);
        return copy;
    }

    @Override
    public Map<Integer, double[]> listNowUserOdds(Integer userId,Integer userLevel, Integer lotteryId,String market) {
        EUserType userType=EUserType.valueOf(userLevel);
        List<UserOddsBO> userOddss=null;
        if(EUserType.GROUP.equals(userType) || EUserType.COMPANY.equals(userType)){
            userOddss=userOddsMapper.listNowHouTongOrCompanyOdds(userId,lotteryId,market);
        }else{
            userOddss=userOddsMapper.listNowAgent1ToAgent10Odds(userId,lotteryId,market);
        }
        Map<Integer, List<UserOddsBO>> mapOdds=userOddss.stream().collect(Collectors.groupingBy(UserOddsBO::getCategoryId));
        Map<Integer, double[]> logic=new HashMap<>();
        for(Map.Entry<Integer,List<UserOddsBO>> entry:mapOdds.entrySet()){
            double[] arrOdds=new double[userLevel+1];
            entry.getValue().stream().forEach(o->arrOdds[o.getUserLevel()]=o.getOdds());
            logic.put(entry.getKey(),arrOdds);
        }
        return logic;
    }


    @Override
    public OddsPlusMinusRQ updateUserOdds(OddsPlusMinusRQ pmo) {
        ExceptionCast.castCheck(pmo.getUserId()==null, CommonCode.INVALID_PARAM);
        ExceptionCast.castCheck(pmo.getOddsIds()==null||pmo.getOddsIds().size()==0, CommonCode.INVALID_PARAM);
        ExceptionCast.castCheck(pmo.getMarkets()==null, CommonCode.INVALID_PARAM);
        ExceptionCast.castCheck(pmo.getDiff()==null, CommonCode.INVALID_PARAM);
        ExceptionCast.castCheck(pmo.getLotteryId()==null, CommonCode.INVALID_PARAM);

        UserInfo user = userService.findBaseById(pmo.getUserId());
        ExceptionCast.castCheck("CLOSE".equals(user.getControlOdds()),CommonCode.UNAUTHORISE);

        log.info("JSON:"+JSON.toJSONString(pmo));
        pmo.setJson(JSON.toJSONString(pmo));
        userOddsMapper.updateUserOdds(pmo);
        //log.info("errcode:"+pmo.getErrcode()+",errmsg:"+pmo.getErrmsg());
        if(pmo.getErrcode()==200){
            HashMap<String,JSONArray> mapJson=new HashMap<>();
            String[] markets=pmo.getMarkets().split(",");
            for(int i=0;i<markets.length;i++){
                String market=markets[i];
                JSONArray jsonArray=new JSONArray();
                JSONArray oddss=JSON.parseArray(pmo.getErrmsg());
                for (Iterator<Object> iterator = oddss.iterator(); iterator.hasNext(); ) {
                    JSONObject odds = (JSONObject) iterator.next();
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("oddsId", odds.getString("oddsId"));
                    jsonObject.put("odds", odds.getDoubleValue("odds"+market.toUpperCase()));
                    jsonArray.add(jsonObject);
                }
                mapJson.put(market,jsonArray);

            }

            for(int i=0;i<markets.length;i++){
                String market=markets[i];
                String groupName= StrUtil.format("{}-{}-{}",pmo.getLotteryId(),pmo.getUserId(),market);
                JSONObject retjson = new JSONObject();
                //retjson.put("userId", pmo.getUserId());
                retjson.put("userLevel", pmo.getUserLevel());
                retjson.put("oddss",mapJson.get(market));
                SendInfo sendInfo = new SendInfo("odds_now", retjson);
                try{
                    tioSocketService.sendToGroup(groupName, sendInfo);
                    log.info("[ {} ] [ odds_now ] : {}", groupName, sendInfo.getContent());
                }catch (Exception e){
                    log.error("[ {} ] [ odds_now ] : {}", groupName, sendInfo.getContent());
                }
            }
        }
        return pmo;
    }

    @Override
    public Boolean updateUserOddsStatus(OddsCloseRQ oc) {
        ExceptionCast.castCheck(oc.getUserId()==null, CommonCode.INVALID_PARAM);
        ExceptionCast.castCheck(oc.getOddsId()==null, CommonCode.INVALID_PARAM);
        ExceptionCast.castCheck(oc.getIsClose()==null, CommonCode.INVALID_PARAM);
        ExceptionCast.castCheck(oc.getLotteryId()==null, CommonCode.INVALID_PARAM);
        Boolean success=userOddsMapper.updateUserOddsStatus(oc);
        if(success){
            String[] markets=new String[]{"A","B","C","D"};
            for(int i=0;i<markets.length;i++) {
                String market = markets[i];
                String groupName = StrUtil.format("{}-{}-{}", oc.getLotteryId(), oc.getUserId(),market);
                JSONObject object = new JSONObject();
                //object.put("userId", oc.getUserId());
                object.put("oddsId", oc.getOddsId());
                object.put("isClose", oc.getIsClose()==1?true:false);
                SendInfo sendInfo = new SendInfo("odds_status", object);
                try{
                    tioSocketService.sendToGroup(groupName, sendInfo);
                    log.info("[ {} ] [ odds_status ] : {}", groupName, sendInfo.getContent());
                }catch (Exception e){
                    e.printStackTrace();
                    log.error("[ {} ] [ odds_status ] : {}", groupName, sendInfo.getContent());
                }
            }
        }
        return success;
    }

    private void JsonIgnoreKind(KindDO kind) {
        kind.setGroupId(null);
        kind.setKindKey(null);
        kind.setIsCljp(null);
        kind.setBuhuoType(null);
        kind.setIsBuhuo(null);
        kind.setDigit(null);
        kind.setOrdering(null);
    }
    private void JsonIgnoreCategory(CategoryDO category) {
        category.setCategoryKey(null);
        category.setOdds(null);
        category.setOrdering(null);
    }
    private void JsonIgnoreGroup(GroupDO group) {
        group.setGroupKey(null);
        group.setOrdering(null);
    }
    private void JsonIgnoreLottery(LotteryVO lottery) {
        lottery.setGroupId(null);
        lottery.setUserId(null);
        lottery.setLotteryKey(null);
        lottery.setDelayOpen(null);
        lottery.setPrecedeClose(null);
        lottery.setZmPrecedeClose(null);
        lottery.setIsOpenSelf(null);
        lottery.setIsLink(null);
        lottery.setIsReset(null);
        lottery.setIsOpenUp(null);
        lottery.setOrdering(null);
    }

}
