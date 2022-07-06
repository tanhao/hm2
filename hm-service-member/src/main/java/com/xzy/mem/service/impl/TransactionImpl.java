package com.xzy.mem.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xzy.common.exception.ExceptionCast;
import com.xzy.common.model.code.MemberCode;
import com.xzy.mem.dao.TranMapper;
import com.xzy.mem.service.TranBhService;
import com.xzy.member.TransactionService;
import com.xzy.pojo.mem.BetRp;
import com.xzy.pojo.mem.BetRq;
import com.xzy.pojo.mem.BetTime;
import com.xzy.pojo.mem.OddsRq;
import com.xzy.utils.GenerateOrderNum;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@DubboService(interfaceClass = TransactionService.class)
@Slf4j
public class TransactionImpl implements TransactionService {
    @Autowired
    TranMapper tranMapper;
    @DubboReference
    TranBhService tranBhService;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Date checkBetTime(Integer lotteryId, Integer userId, Long gameNo) {
        BetTime betTime = tranMapper.selectBetTime(lotteryId, userId, gameNo);
        if (null == betTime) {
            //如果查询出来为null，说明该彩种不存在或者无权限
            log.info("/{}/{}/{}未查询到下注彩种", lotteryId, gameNo,userId);
            ExceptionCast.cast(MemberCode.LOTTERY_CLOSE);
        }
        if (betTime.getStatus().equals("DIVIDEND")) {
            log.info("/{}/{}已开奖", lotteryId, gameNo);
            ExceptionCast.cast(MemberCode.PAYOUT_ALREADY);
        }
        Long nowTime = System.currentTimeMillis();
        if (betTime.getStartTime() * 1000 > nowTime) {
            log.info("/{}/{}/投注时间{}/参数[{}]", lotteryId, gameNo, sdf.format(betTime.getStartTime() * 1000), betTime);
            ExceptionCast.cast(MemberCode.NOT_OPEN);
        }
        if (betTime.getStopTime() * 1000 < nowTime) {
            log.info("/{}/{}/封盘时间{}/参数[{}]", lotteryId, gameNo, sdf.format(betTime.getStopTime() * 1000), betTime);
            ExceptionCast.cast(MemberCode.DRAWING_NOT_BET);
        }
        return betTime.getAccountDay();
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public Map execQueueBet(Integer userId, BetRq betRq) {
        long st = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        List<OddsRq> list = betRq.getBetOdds();
        Map<String, Object> resMap = new HashMap<>();
        //已经暂停的彩种
        if (betRq.getLotteryId() == 301 || betRq.getLotteryId() == 302 || betRq.getLotteryId() == 303 || betRq.getLotteryId() == 304) {
            for (OddsRq oddsRp : list) {
                if (oddsRp.getBetContent() != null && !oddsRp.getBetContent().equals("")) {
                    String oddsNumArr[] = oddsRp.getBetContent().split(",");
                    Set<String> set = new HashSet<String>();
                    for (String i : oddsNumArr) {
                        set.add(i);
                    }
                    if (set.size() != oddsNumArr.length) {
                        resMap.put("code", MemberCode.BET_KLSF_ERROR.code());
                        return resMap;
                    }
                }
            }
            log.info("/{}/{}暂停的彩种", betRq,userId);
        }
        //限制一次投注最大注单量
        if (list.size() > 400) {
            log.info("/{}/{}超过注单量上限400", betRq,userId);
            resMap.put("code", MemberCode.BET_SIZE_ERROR.code());
            return resMap;
        }
        log.info(betRq.toString());
        log.info("oddsId集合[{}]", CollectionUtil.join(betRq.getBetOdds().stream().map(OddsRq::getOddsId).collect(Collectors.toList()), ","));
        JSONArray array = new JSONArray();
        for (OddsRq oddsRp : list) {
            JSONObject json = new JSONObject();
            json.put("oddsId", oddsRp.getOddsId());
            json.put("betAmt", oddsRp.getBetAmt());
            json.put("betOdds", oddsRp.getOdds());
            json.put("betContent", oddsRp.getBetContent());
            array.add(json);
        }
        BigDecimal totalBet = list.stream().map(OddsRq::getBetAmt).reduce(BigDecimal.ZERO, BigDecimal::add);
        BetRp betRp = new BetRp();
        betRp.setUserId(userId);
        betRp.setUniqueId(GenerateOrderNum.generate());
        betRp.setLotteryId(betRq.getLotteryId());
        betRp.setGameNo(betRq.getGameNo());
        betRp.setAutoNewOdds(betRq.getAutoNewOdds());
        betRp.setBetTotalAmt(totalBet);
        betRp.setBetSource(betRq.getBetSource());
        betRp.setBetInfo(array.toJSONString());
        betRp.setResultAccountDay(betRq.getResultAccountDay());
        tranMapper.execBet(betRp);
        int betStatus = betRp.getErrcode();
        resMap.put("code", betStatus);
        if (betStatus == 200) {
            tranBhService.calculateStats(betRp);
        } else {
            //失败代码,返回错误信息
            log.info("注单下注失败[{}],用户ID[{}]",betRp,userId);
            resMap.put("keys", betRp.getKeys());
        }
        long et = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        log.info("注单[{}]状态[{}]数量[{}]执行时间[{}]", betRp.getUniqueId(), betStatus, list.size(), et - st);
        return resMap;

    }


}
