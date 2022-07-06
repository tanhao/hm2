package com.xzy.ctrl.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xzy.common.exception.ExceptionCast;
import com.xzy.common.model.code.CommonCode;
import com.xzy.common.model.response.ResponseData;
import com.xzy.ctrl.*;
import com.xzy.ctrl.dao.UserBuhuoMapper;
import com.xzy.ctrl.dao.UserRegressMapper;
import com.xzy.enums.EUserType;
import com.xzy.pojo.ctrl.*;
import com.xzy.pojo.ctrl.bo.UserStatsBO;
import com.xzy.pojo.ctrl.request.ManualBuhuoRQ;
import com.xzy.pojo.ctrl.vo.LotteryVO;
import com.xzy.pojo.ctrl.vo.NowOrderVO;
import com.xzy.pojo.user.HmUser;
import com.xzy.pojo.user.response.UserInfo;
import com.xzy.user.UserService;
import com.xzy.utils.GenerateOrderNum;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 补货业务逻辑层
 */
@DubboService
@SuppressWarnings("all")
@Slf4j
public class NowOrderServiceImpl implements NowOrderService {


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
    private PlayService playService;
    @DubboReference
    private UserOddsService userOddsService;
    @DubboReference
    private LotteryService lotteryService;
    @DubboReference
    private OddsService oddsService;
    @DubboReference
    private UserLotteryService userLotteryService;
    @DubboReference
    private UserStepService userStepService;
    @DubboReference
    private UserService userService;
    @DubboReference
    private UserOddsNowService userOddsNowService;
    @DubboReference
    private UserOddsJumpService userOddsJumpService;
    @DubboReference
    private UserOddsCljpService userOddsCljpService;
    @DubboReference
    private UserOddsCloseService userOddsCloseService;
    @DubboReference
    private UserStatsService userStatsService;
    @Autowired
    private UserBuhuoMapper userBuhuoMapper;

    @Override
    public NowOrderVO getFrontOdds(Integer userId,Integer lotteryId) {
        try{
            log.info("=======>>> getFrontOdds start......................");
            UserInfo user = userService.findBaseById(userId);
            LotteryVO lotteryVO=userLotteryService.getLotteryVO(userId,lotteryId);
            Map<Integer, KindDO> mapKinds = kindService.mapKinds(lotteryVO.getGroupId());
            Map<Integer, CategoryDO> mapCategorys = categoryService.mapGategorys(mapKinds);
            Map<Integer, PlayDO> mapPlays = playService.mapPlays(lotteryId);
            List<OddsDO> oddss=oddsService.listOddss(mapPlays);

            String market=user.getMarketOpen();
            Map<Integer, double[]> userOddss= userOddsService.listNowUserOdds(userId,user.getUserLevel(),lotteryId,market);


            Map<Integer, double[]> userOddsNows= userOddsNowService.listNowUserOddsNow(userId,user.getUserLevel(),lotteryId,market);
            Map<Integer, double[]> userOddsJumps= userOddsJumpService.listNowUserOddsJump(userId,user.getUserLevel(),lotteryId);
            Map<Integer, double[]> userOddsCljps= userOddsCljpService.listNowUserOddsCljp(userId,user.getUserLevel(),lotteryId);
            Map<Integer, Boolean> userOddsClose= userOddsCloseService.listNowUserOddsClose(userId,lotteryId);

            mapKinds.values().forEach(kind ->this.JsonIgnoreKind(kind));
            mapCategorys.values().forEach(category ->  this.JsonIgnoreCategory(category));
            mapPlays.values().forEach(play ->this.JsonIgnorePlay(play));
            this.JsonIgnoreLotterySingle(lotteryVO);

            NowOrderVO nowOrderVO=new NowOrderVO();
            nowOrderVO.setKinds(mapKinds);
            nowOrderVO.setCategorys(mapCategorys);
            nowOrderVO.setLottery(lotteryVO);
            nowOrderVO.setOddss(oddss);
            nowOrderVO.setUserOddss(userOddss);
            nowOrderVO.setUserOddsNows(userOddsNows);
            nowOrderVO.setUserOddsJumps(userOddsJumps);
            nowOrderVO.setUserOddsCljps(userOddsCljps);
            nowOrderVO.setUserOddsCloses(userOddsClose);
            nowOrderVO.setPlays(mapPlays);
            log.info("=======>>> getFrontOdds end......................");
            return nowOrderVO;
        }catch (Exception e){
            e.printStackTrace();
        }
        return new NowOrderVO();
    }

    @Override
    public NowOrderVO getFrontNowStats(Integer userId, Integer lotteryId){
        HmUser user = userService.findById(userId);
        Map<Integer, double[]> userOddsJumps= userOddsJumpService.listNowUserOddsJump(userId,user.getUserLevel(),lotteryId);
        NowOrderVO nowOrderVO=new NowOrderVO();
        nowOrderVO.setUserOddsJumps(userOddsJumps);
        return nowOrderVO;
    }

    @Override
    public NowOrderVO getNowOrderVO(Integer loginUserId, Integer userId, Integer lotteryId, String market, String markets, String buhuo) {
        try{
            log.info("=======>>> getNowOrderVO start......................");
            UserInfo user = userService.findBaseById(userId);
            HmUser user2 = userService.findById(userId);
            String[] arrMarkets=user.getMarketOpen().split(",");
            market=market==null?arrMarkets[0]:market;
            markets=markets==null?user.getMarketOpen():markets;
            buhuo=buhuo==null?"SZ":buhuo;

            List<LotteryVO> listLotterys = userLotteryService.listLotteryVOs(userId);
            listLotterys.sort((a1, a2) -> a1.getOrdering().compareTo(a2.getOrdering()));
            lotteryId=lotteryId==null?listLotterys.get(0).getLotteryId():lotteryId;
            LotteryVO lotteryVO=userLotteryService.getLotteryVO(userId,lotteryId);
            Map<Integer, KindDO> mapKinds = kindService.mapKinds(lotteryVO.getGroupId());
            Map<Integer, CategoryDO> mapCategorys = categoryService.mapGategorys(mapKinds);
            Map<Integer, PlayDO> mapPlays = playService.mapPlays(lotteryId);
            List<OddsDO> oddss=oddsService.listOddss(mapPlays);
            Map<Integer, double[]> userOddss= userOddsService.listNowUserOdds(userId,user.getUserLevel(),lotteryId,market);
            List<UserStepDO> userStepDOs= userStepService.listUserStepDOsByLotteryId(userId,lotteryId);
            Map<Integer, UserStepDO> mapStep=userStepDOs.stream().collect(Collectors.toMap(UserStepDO::getCategoryId, Function.identity()));
            Map<String,Boolean> mapMarkets= Arrays.asList(arrMarkets).stream().collect(Collectors.toMap(item->item, item->true));

            Map<Integer, double[]> userOddsNows= userOddsNowService.listNowUserOddsNow(userId,user.getUserLevel(),lotteryId,market);
            Map<Integer, double[]> userOddsJumps= userOddsJumpService.listNowUserOddsJump(userId,user.getUserLevel(),lotteryId);
            Map<Integer, double[]> userOddsCljps= userOddsCljpService.listNowUserOddsCljp(userId,user.getUserLevel(),lotteryId);
            Map<Integer, Boolean> userOddsClose= userOddsCloseService.listNowUserOddsClose(userId,lotteryId);

            List<UserStatsBO> userStatsBOs=userStatsService.listUserStatsBOs(userId,lotteryId,lotteryVO.getGameNo(),buhuo,markets);
            Map<Integer, UserStatsBO> userStats=userStatsBOs.stream().collect(Collectors.toMap(UserStatsDO::getOddsId,Function.identity()));

            List<UserStatsBO> userStatsLoteryBOs=userStatsService.listUserStatsBOsGroupByLotteryId(userId,user2.getCompanyId(),buhuo,markets);
            Map<Integer, UserStatsBO> userStatsLottery=userStatsLoteryBOs.stream().collect(Collectors.toMap(UserStatsDO::getLotteryId,Function.identity()));

            Double winlose=userStatsService.getTodayWinLose(userId);

            mapKinds.values().forEach(kind ->this.JsonIgnoreKind(kind));
            mapCategorys.values().forEach(category ->  this.JsonIgnoreCategory(category));
            mapPlays.values().forEach(play ->this.JsonIgnorePlay(play));
            mapStep.values().forEach(step ->this.JsonIgnoreStep(step));
            listLotterys.forEach(lottery ->  this.JsonIgnoreLottery(lottery));
            this.JsonIgnoreLotterySingle(lotteryVO);

            NowOrderVO nowOrderVO=new NowOrderVO();
            nowOrderVO.setKinds(mapKinds);
            nowOrderVO.setCategorys(mapCategorys);
            nowOrderVO.setLotterys(listLotterys);
            nowOrderVO.setLottery(lotteryVO);
            nowOrderVO.setOddss(oddss);
            nowOrderVO.setUserOddss(userOddss);
            nowOrderVO.setUserOddsNows(userOddsNows);
            nowOrderVO.setUserOddsJumps(userOddsJumps);
            nowOrderVO.setUserOddsCljps(userOddsCljps);
            nowOrderVO.setUserOddsCloses(userOddsClose);
            nowOrderVO.setUserStats(userStats);
            nowOrderVO.setUserStatsLottery(userStatsLottery);
            nowOrderVO.setPlays(mapPlays);
            nowOrderVO.setUserSteps(mapStep);
            nowOrderVO.setMarkets(mapMarkets);
            nowOrderVO.setMarket(market);
            nowOrderVO.setBuhuo(buhuo);
            nowOrderVO.setCanEdit(loginUserId.equals(userId)&&user.getUserLevel()>=EUserType.COMPANY.getLevel()&&"OPEN".equals(user.getControlOdds()));
            nowOrderVO.setCanCloseOpen(loginUserId.equals(userId)&&user.getUserLevel().equals(EUserType.COMPANY.getLevel()));
            nowOrderVO.setWinlose(winlose);
            log.info("=======>>> getNowOrderVO end......................");
            return nowOrderVO;
        }catch (Exception e){
            e.printStackTrace();
        }
        return new NowOrderVO();
    }

    @Override
    public NowOrderVO getNowLotteryVO(Integer userId, Integer lotteryId,String market) {
        UserInfo user = userService.findBaseById(userId);
        String logicMarket=market;
        if("MEMBER".equals(user.getUserType())){
            logicMarket=user.getMarketOpen();
        }
        LotteryVO lotteryVO=userLotteryService.getLotteryVO(userId,lotteryId);
        this.JsonIgnoreLotterySingle(lotteryVO);
        Map<Integer, double[]> userOddsNows= userOddsNowService.listNowUserOddsNow(userId,user.getUserLevel(),lotteryId,logicMarket);
        NowOrderVO nowOrderVO=new NowOrderVO();
        nowOrderVO.setLottery(lotteryVO);
        nowOrderVO.setUserOddsNows(userOddsNows);
        return nowOrderVO;
    }
    @Override
    public NowOrderVO getNowStats(Integer userId, Integer lotteryId,String gameNo,String buhuo,String markets){
        HmUser user = userService.findById(userId);
        List<UserStatsBO> userStatsBOs=userStatsService.listUserStatsBOs(userId,lotteryId,gameNo,buhuo,markets);
        Map<Integer, UserStatsBO> userStats=userStatsBOs.stream().collect(Collectors.toMap(UserStatsDO::getOddsId,Function.identity()));
        List<UserStatsBO> userStatsLoteryBOs=userStatsService.listUserStatsBOsGroupByLotteryId(userId,user.getCompanyId(),buhuo,markets);
        Map<Integer, UserStatsBO> userStatsLottery=userStatsLoteryBOs.stream().collect(Collectors.toMap(UserStatsDO::getLotteryId,Function.identity()));
        Double winlose=userStatsService.getTodayWinLose(userId);
         //Map<Integer, double[]> userOddsJumps= userOddsJumpService.listNowUserOddsJump(userId,user.getUserLevel(),lotteryId);
        NowOrderVO nowOrderVO=new NowOrderVO();
        nowOrderVO.setUserStats(userStats);
        nowOrderVO.setUserStatsLottery(userStatsLottery);
        nowOrderVO.setWinlose(winlose);
        //nowOrderVO.setUserOddsJumps(userOddsJumps);
        return nowOrderVO;
    }
    @Override
    public NowOrderVO getNowOdds(Integer userId, Integer lotteryId,String gameNo,String buhuo,String market,String markets){
        UserInfo user = userService.findBaseById(userId);
        Map<Integer, double[]> userOddss= userOddsService.listNowUserOdds(userId,user.getUserLevel(),lotteryId,market);
        Map<Integer, double[]> userOddsNows= userOddsNowService.listNowUserOddsNow(userId,user.getUserLevel(),lotteryId,market);
        //Map<Integer, double[]> userOddsJumps= userOddsJumpService.listNowUserOddsJump(userId,user.getUserLevel(),lotteryId,market);
        //Map<Integer, double[]> userOddsCljps= userOddsCljpService.listNowUserOddsCljp(userId,user.getUserLevel(),lotteryId,market);
        List<UserStatsBO> userStatsBOs=userStatsService.listUserStatsBOs(userId,lotteryId,gameNo,buhuo,markets);
        Map<Integer, UserStatsBO> userStats=userStatsBOs.stream().collect(Collectors.toMap(UserStatsDO::getOddsId,Function.identity()));
        //List<UserStatsBO> userStatsBOs2=userStatsService.listUserStatsBOsGroupByLotteryId(userId,buhuo,markets);
        //Map<Integer, UserStatsBO> userStats2=userStatsBOs2.stream().collect(Collectors.toMap(UserStatsDO::getLotteryId,Function.identity()));
        NowOrderVO nowOrderVO=new NowOrderVO();
        //nowOrderVO.setUserStatsLottery(userStats2);
        nowOrderVO.setUserOddss(userOddss);
        nowOrderVO.setUserOddsNows(userOddsNows);
        nowOrderVO.setUserStats(userStats);
        return nowOrderVO;
    }

    @Override
    public Integer getManualBuhuoAmt(ManualBuhuoRQ buhuo) {
        ExceptionCast.castCheck(ObjectUtil.isNull(buhuo.getUserId()), CommonCode.INVALID_PARAM);
        ExceptionCast.castCheck(ObjectUtil.isNull(buhuo.getLotteryId()), CommonCode.INVALID_PARAM);
        ExceptionCast.castCheck(ObjectUtil.isNull(buhuo.getGameNo()), CommonCode.INVALID_PARAM);
        ExceptionCast.castCheck(ObjectUtil.isNull(buhuo.getOddsId()), CommonCode.INVALID_PARAM);
        Integer amt=userBuhuoMapper.getManualBuhuoAmt(buhuo);
        return amt;
    }

    @Override
    public void manualBuhuo(ManualBuhuoRQ buhuo) {
        ExceptionCast.castCheck(ObjectUtil.isNull(buhuo.getUserId()), CommonCode.INVALID_PARAM);
        ExceptionCast.castCheck(ObjectUtil.isNull(buhuo.getLotteryId()), CommonCode.INVALID_PARAM);
        ExceptionCast.castCheck(ObjectUtil.isNull(buhuo.getGameNo()), CommonCode.INVALID_PARAM);
        ExceptionCast.castCheck(ObjectUtil.isNull(buhuo.getMarket()), CommonCode.INVALID_PARAM);
        ExceptionCast.castCheck(ObjectUtil.isNull(buhuo.getOddsId()), CommonCode.INVALID_PARAM);

        JSONObject json=new JSONObject();
        json.put("oddsId",buhuo.getOddsId());
        json.put("betOdds",buhuo.getBetOdds());
        json.put("betAmt",buhuo.getBetAmt());
        json.put("betContent",buhuo.getBetContent());
        JSONArray jsonArray=new JSONArray();
        jsonArray.add(json);
        buhuo.setUniqueId(GenerateOrderNum.generate());
        buhuo.setJson(jsonArray.toJSONString());
        log.info(JSON.toJSONString(buhuo));
        ExceptionCast.castCheck(buhuo.getBetAmt()<=0, CommonCode.INVALID_PARAM);
        userBuhuoMapper.manualBuhuo(buhuo);
        if(buhuo.getErrcode()!=200){
            ResponseData.ERROR(CommonCode.SERVER_ERROR);
        }
    }


    private void JsonIgnorePlay(PlayDO play) {
        play.setPlayId(null);
        play.setLotteryId(null);
        play.setOrdering(null);
    }
    private void JsonIgnoreKind(KindDO kind) {
        kind.setKindId(null);
        kind.setGroupId(null);
        kind.setQuickSettingId(null);
        kind.setIsCljp(null);
        kind.setBuhuoType(null);
        kind.setIsBuhuo(null);
        kind.setDigit(null);
        kind.setOrdering(null);
    }
    private void JsonIgnoreStep(UserStepDO step) {
        step.setCategoryId(null);
        step.setUserId(null);
        step.setLotteryId(null);
    }
    private void JsonIgnoreCategory(CategoryDO category) {
        category.setCategoryId(null);
        category.setOdds(null);
        category.setOrdering(null);
        category.setStep(null);
    }
    private void JsonIgnoreLottery(LotteryVO lottery) {
        lottery.setGroupId(null);
        lottery.setUserId(null);
        lottery.setDelayOpen(null);
        lottery.setPrecedeClose(null);
        lottery.setZmPrecedeClose(null);
        lottery.setIsOpenSelf(null);
        lottery.setIsLink(null);
        lottery.setIsReset(null);
        lottery.setIsOpenUp(null);
        lottery.setOrdering(null);
        lottery.setResultSize(null);
        lottery.setOpenTime(null);
        lottery.setActionTime(null);
        lottery.setGameNo(null);
        lottery.setCodeNums(null);
    }
    private void JsonIgnoreLotterySingle(LotteryVO lottery) {
        lottery.setUserId(null);
        lottery.setIsOpenSelf(null);
        lottery.setIsOpenUp(null);
        lottery.setOrdering(null);
        lottery.setResultSize(null);
        lottery.setCodeNums(null);
    }

}
