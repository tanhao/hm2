package com.xzy.smart.service;

import cn.hutool.core.util.StrUtil;
import com.xzy.common.exception.ExceptionCast;
import com.xzy.common.model.code.CommonCode;
import com.xzy.enums.SmartEnums;
import com.xzy.pojo.mem.BetRq;
import com.xzy.pojo.mem.OddsRq;
import com.xzy.pojo.result.HmResult;
import com.xzy.pojo.smart.HmLocationBet;
import com.xzy.pojo.smart.HmLocationPlan;
import com.xzy.pojo.smart.HmPlan;
import com.xzy.smart.code.SmartCode;
import com.xzy.smart.dao.HmLocationBetMapper;
import com.xzy.smart.dao.HmLocationPlanMapper;
import com.xzy.smart.dao.HmPlanMapper;
import com.xzy.smart.dao.SmartMapper;
import com.xzy.smart.vo.LocationPlanVo;
import com.xzy.smart.vo.SmartVo;
import com.xzy.smart.vo.StopVo;
import com.xzy.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class SmartService {
    @Autowired
    HmLocationBetMapper hmLocationBetMapper;
    @Autowired
    HmLocationPlanMapper hmLocationPlanMapper;
    @Autowired
    HmPlanMapper hmPlanMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    private final String SMART = "smart";
    @Autowired
    SmartMapper smartMapper;
    @Autowired
    SmartBetService smartBetService;

    @Transactional
    public void savePlan(SmartVo smartVo) {
        //1添加计划
        HmPlan hmPlan = new HmPlan();
        HmLocationBet hmLocationBet = new HmLocationBet();

        BeanUtils.copyProperties(smartVo, hmPlan);
        ExceptionCast.castCheck(StrUtil.isBlank(smartVo.getPlanName()), SmartCode.PLAN_NAME_NOT_NULL);
        int i = hmPlanMapper.insertSelective(hmPlan);
        ExceptionCast.castCheck(i == 0, SmartCode.PLAN_ADD_ERROR);
        //2添加计划功能选着
        hmLocationBet.setLotteryId(hmPlan.getLotteryId());
        hmLocationBet.setUserId(hmPlan.getUserId());
        hmLocationBet.setPlanId(hmPlan.getPlanId());
        hmLocationBet.setBetModel(smartVo.getBetModel());
        hmLocationBet.setFixedContent(smartVo.getFixedContent());
        hmLocationBet.setWlChoice(smartVo.getWlChoice());
        hmLocationBetMapper.insertSelective(hmLocationBet);

        //3添加计划内容位置数据
        List<LocationPlanVo> plans = smartVo.getLocations();
        plans.forEach(plan -> {
            HmLocationPlan hmLocationPlan = new HmLocationPlan();
            hmLocationPlan.setPlayId(plan.getPlayId());
            hmLocationPlan.setAmount(plan.getAmount());
            hmLocationPlan.setNumbers(plan.getNumbers());
            hmLocationPlan.setLotteryId(hmPlan.getLotteryId());
            hmLocationPlan.setUserId(hmPlan.getUserId());
            hmLocationPlan.setPlanId(hmPlan.getPlanId());
            String oddss = smartMapper.selectOddss(plan.getPlayId(), plan.getNumbers().split(","));
            hmLocationPlan.setOddss(oddss);
            hmLocationPlanMapper.insertSelective(hmLocationPlan);
        });
    }

    public List<HmPlan> getSmartPlan(Integer loginId, Integer lotteryId) {
        ExceptionCast.castCheck(lotteryId == null, CommonCode.INVALID_PARAM);
        HmPlan hmPlan = new HmPlan();
        hmPlan.setLotteryId(lotteryId);
        hmPlan.setUserId(loginId);
        return hmPlanMapper.select(hmPlan);
    }

    public SmartVo getPlanDetail(Integer loginId, Integer planId) {
        ExceptionCast.castCheck(planId == null, CommonCode.INVALID_PARAM);
        //查询计划
        HmPlan hmPlan = hmPlanMapper.selectByPrimaryKey(planId);
        if (hmPlan != null && loginId.equals(hmPlan.getUserId())) {
            SmartVo smartVo = new SmartVo();
            BeanUtils.copyProperties(hmPlan, smartVo);

            //查询计划功能选着
            HmLocationBet hmLocationBet = hmLocationBetMapper.selectByPrimaryKey(planId);
            smartVo.setBetModel(hmLocationBet.getBetModel());
            smartVo.setFixedContent(hmLocationBet.getFixedContent());
            smartVo.setWlChoice(hmLocationBet.getWlChoice());

            //查询计划内容位置数据
            HmLocationPlan hmLocationPlan = new HmLocationPlan();
            hmLocationPlan.setPlanId(planId);
            List<HmLocationPlan> plans = hmLocationPlanMapper.select(hmLocationPlan);
            smartVo.setLocations(new ArrayList<>());
            plans.forEach(plan -> {
                LocationPlanVo planVo = new LocationPlanVo();
                planVo.setPlayId(plan.getPlayId());
                planVo.setAmount(plan.getAmount());
                planVo.setNumbers(plan.getNumbers());
                smartVo.getLocations().add(planVo);
            });

            return smartVo;
        }
        return null;
    }

    public void taskSmart() {
        String openSmart = redisTemplate.opsForValue().get("openSmart").toString();
        if("1".equals(openSmart)){
            return;
        }
        List<HmPlan> hmPlans = hmPlanMapper.selectPlan();
        for (HmPlan plan : hmPlans) {
            //if (!plan.getUserId().equals(4221)) continue;
            //查询当期和上期期号
            HmResult lotteryVO = smartMapper.selectCurrentGameNo(plan.getCompanyId(), plan.getLotteryId(), DateUtil.getAccountDay().toString());
            if (lotteryVO == null) {
                continue;
            }
            //查询redis中的执行计划
            List<String> gameNos = (List<String>) redisTemplate.boundHashOps(SMART).get(plan.getPlanId().toString());
            String lastGameNo = "";
            if (gameNos == null) {
                gameNos = new ArrayList<>();
            } else {
                lastGameNo = gameNos.get(gameNos.size() - 1);
            }
            gameNos.add(plan.getGameNo());
            //计划中生效的期号是当期的上一期才能生效
            if (plan.getGameNo().compareTo(lotteryVO.getGameNo()) < 0) {
                if (gameNos.size() > 1000) {
                    for (int i = 0; i < 100; i++) {
                        gameNos.remove(i);
                    }
                }
                this.upGameNo(gameNos, plan.getPlanId(), lotteryVO.getGameNo());
                //查询计划功能选着
                HmLocationBet hmLocationBet = hmLocationBetMapper.selectByPrimaryKey(plan.getPlanId());
                if (hmLocationBet != null) {
                    if (hmLocationBet.getBetModel().equals(SmartEnums.BetModel.quota.toString())) {
                        //定额投注，启动后按设置金额每期固定投注
                        HmLocationPlan quotaPlan = new HmLocationPlan();
                        quotaPlan.setPlanId(plan.getPlanId());
                        List<HmLocationPlan> quotaPlans = hmLocationPlanMapper.select(quotaPlan);
                        BetRq betRq = new BetRq();
                        betRq.setLotteryId(plan.getLotteryId());
                        betRq.setGameNo(Long.valueOf(lotteryVO.getGameNo()));
                        betRq.setBetSource(SmartEnums.BetSource.smart.toString());
                        ZonedDateTime zonedDateTime = lotteryVO.getAccountDay().atStartOfDay(ZoneId.systemDefault());
                        betRq.setResultAccountDay(Date.from(zonedDateTime.toInstant()));
                        List<OddsRq> betOdds = new ArrayList<>();
                        quotaPlans.forEach(item -> {
                            String[] split = item.getOddss().split(",");
                            for (String oddsId : split) {
                                OddsRq oddsRq = new OddsRq();
                                oddsRq.setBetAmt(BigDecimal.valueOf(item.getAmount()));
                                oddsRq.setOddsId(Integer.valueOf(oddsId));
                                oddsRq.setOdds(BigDecimal.ZERO);
                                betOdds.add(oddsRq);
                            }
                        });
                        if (betOdds.size() > 0) {
                            betRq.setBetOdds(betOdds);
                            log.info("[{}]执行投注[{}]",plan.getPlanId(),betRq.toString());
                            //异步调用投注方法
                            smartBetService.queueBet(plan.getPlanId(), plan.getUserId(), betRq);
                        }
                    } else if (hmLocationBet.getBetModel().equals(SmartEnums.BetModel.fixed.toString())) {
                        //倍数投注
                        //验证上期注单是否完成
                        Boolean waiting = smartMapper.checkOrderWaiting(plan.getUserId(), plan.getGameNo());
                        //未完成直接结束，等第二次查询
                        if (waiting) return;
                        //完成，说明有结果了
                        if (hmLocationBet.getWlChoice().equals(SmartEnums.WlChoice.win.toString())) {

                        } else if (hmLocationBet.getWlChoice().equals(SmartEnums.WlChoice.lose.toString())) {

                        }
                        String[] content = hmLocationBet.getFixedContent().split(",");
                    }
                }

            }
        }
    }

    //修改下一期期号
    public void upGameNo(List<String> gameNos, Integer planId, String gameNo) {
        HmPlan plan = new HmPlan();
        plan.setPlanId(planId);
        plan.setGameNo(gameNo);
        hmPlanMapper.updateByPrimaryKeySelective(plan);
        //存入redis数据
        redisTemplate.boundHashOps(SMART).put(plan.getPlanId().toString(), gameNos);
    }


    /**
     * 删除计划
     *
     * @param loginId
     * @param planId
     */
    public void deletePlan(Integer loginId, Integer planId) {
        ExceptionCast.castCheck(planId == null, CommonCode.INVALID_PARAM);
        //查询计划
        HmPlan hmPlan = hmPlanMapper.selectByPrimaryKey(planId);
        if (hmPlan != null && loginId.equals(hmPlan.getUserId())) {
            hmPlanMapper.deleteByPrimaryKey(planId);
        }
    }

    /**
     * 切换计划状态
     *
     * @param loginId
     * @param planId
     */
    public Boolean upPlanStatus(Integer loginId, Integer planId) {
        ExceptionCast.castCheck(planId == null, CommonCode.INVALID_PARAM);
        StopVo stopVo = hmPlanMapper.selectStopData(DateUtil.getAccountDay().toString(), loginId);
        //如果为空说明今日最开始没有投注数据，可以直接修改状态
        if (stopVo != null) {
            //验证止盈止亏
            if (stopVo.getBetWinAmt().compareTo(stopVo.getStopProfit()) >= 0) {
                String logs = "今日止盈已达最大额度:" + stopVo.getStopProfit() + "，无法进行修改";
                ExceptionCast.cast(logs);
            }
            if (stopVo.getBetWinAmt().multiply(new BigDecimal(-1)).compareTo(stopVo.getStopLoss()) >= 0) {
                String logs = "今日止损已达最大额度:" + stopVo.getStopLoss() + "，无法进行修改";
                ExceptionCast.cast(logs);
            }
        }
        //查询计划
        HmPlan hmPlan = hmPlanMapper.selectByPrimaryKey(planId);
        if (hmPlan != null && loginId.equals(hmPlan.getUserId())) {
            Boolean planStatus = !hmPlan.getPlanStatus();
            HmPlan upPlan = new HmPlan();
            upPlan.setPlanId(planId);
            upPlan.setPlanStatus(planStatus);
            hmPlanMapper.updateByPrimaryKeySelective(upPlan);
            return planStatus;
        }
        ExceptionCast.cast("修改失败");
        return false;
    }

}
