package com.xzy.ctrl.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xzy.common.exception.ExceptionCast;
import com.xzy.common.model.code.CommonCode;
import com.xzy.common.model.code.CtrlCode;
import com.xzy.ctrl.UserZhuanOddsService;
import com.xzy.ctrl.dao.UserOddsMapper;
import com.xzy.pojo.ctrl.UserCategoryDO;
import com.xzy.pojo.ctrl.UserKindDO;
import com.xzy.pojo.ctrl.request.DiffOddsRQ;
import com.xzy.pojo.ctrl.request.OddsRegressLimitFulloddsRQ;
import com.xzy.pojo.ctrl.vo.ZhuanOddsVO;
import com.xzy.pojo.tio.entity.SendInfo;
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
import java.util.stream.Collectors;

/**
 * 补货业务逻辑层
 */
@DubboService
@Slf4j
@SuppressWarnings("all")
public class UserZhuanOddsServiceImpl extends UserCategoryServiceImpl implements UserZhuanOddsService {

    @Autowired
    private UserOddsMapper userOddsMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @DubboReference
    private UserService userService;
    @DubboReference
    TioSocketService tioSocketService;

    private final String USER_ZHUAN_ODDS = "zhuan_odds:{}";
    private final String LOCK_USER_ZHUAN_ODDS = "logck:zhuan_odds:{}";

    @Override
    public ZhuanOddsVO getZhuanOddsVO(Integer userId) {
        ZhuanOddsVO zhuanOddsVO =this.getCategoryVO(new ZhuanOddsVO(),userId);
        UserInfo user=userService.findBaseById(userId);
        UserInfo upper=userService.findBaseById(user.getParentId());
        Map<String,Boolean> markets= Arrays.asList(upper.getMarketOpen().split(",")).stream().collect(Collectors.toMap(item->item, item->true));
        zhuanOddsVO.setMarkets(markets);
        //UserInfo user=userService.findBaseById(userId);
        //Double maxDiff=user.getMaxDiff()/100.0;
        //zhuanOddsVO.setMaxDiff(maxDiff);
        return zhuanOddsVO;
    }

    @Override
    public OddsRegressLimitFulloddsRQ updateZhuanOdds(OddsRegressLimitFulloddsRQ orlf) {
        ExceptionCast.castCheck(orlf.getUserId()==null,CtrlCode.USER_ID_NULL);
        ExceptionCast.castCheck(orlf.getOddss().size()<=0,CtrlCode.NO_CHANGES);
        UserInfo user = userService.findBaseById(orlf.getLoginUserId());
        ExceptionCast.castCheck("CLOSE".equals(user.getGainOdds()),CommonCode.UNAUTHORISE);
        //HmUser user=userService.findById(orlf.getUserId());
        log.info("JSON:"+JSON.toJSONString(orlf));
        orlf.setJson(JSON.toJSONString(orlf));
        userOddsMapper.updateZhuanOdds(orlf);
        if(orlf.getErrcode()==200){
            String[] markets=new String[]{"A","B","C","D"};
            HashMap<String, JSONArray> mapJson=new HashMap<>();
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
    public String getRedisKey() {
        return USER_ZHUAN_ODDS;
    }
    @Override
    public String getLockKey() {
        return LOCK_USER_ZHUAN_ODDS;
    }
    @Override
    public List<? extends UserKindDO> listUserKindDO(Integer userId) {
        return new ArrayList<>();
    }

    @Override
    public List<? extends UserCategoryDO> listUserCategoryDO(Integer userId) {
        return userOddsMapper.listZhuanOdds(userId);
    }
}
