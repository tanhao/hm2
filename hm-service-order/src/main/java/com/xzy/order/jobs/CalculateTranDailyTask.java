package com.xzy.order.jobs;

import com.xzy.order.dao.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
@Slf4j
public class CalculateTranDailyTask {
    @Autowired
    OrderMapper orderMapper;

    /**
     * 统计每日注单
     */
    @Scheduled(fixedDelay = 5000)
    public void fixedDelayDaily() {
        long st = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        orderMapper.splCalculateOrderDaily();
        long ed = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        if(ed - st>5000)
        log.info("计算每日时间->" + (ed - st));
    }

    /**
     * 统计每日补货
     */
    @Scheduled(fixedDelay = 5000)
    public void fixedDelayDailyBh() {
        long st = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        orderMapper.splCalculateOrderDailyBh();
        long ed = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        if(ed - st>5000)
        log.info("计算补货时间->" + (ed - st));
    }

    @Scheduled(fixedDelay = 5000)
    public void fixedPayoutPrizeCheck() {
        long st = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        orderMapper.splPayoutPrizeCheck();
        long ed = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        if(ed - st>5000)
        log.info("计算队列后入单时间->" + (ed - st));
    }

}
