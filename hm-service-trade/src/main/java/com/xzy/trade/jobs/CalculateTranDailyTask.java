package com.xzy.trade.jobs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@EnableScheduling
@Component
@Slf4j
public class CalculateTranDailyTask {

    /**
     * 统计每日注单
     */
    @Scheduled(fixedDelay = 5000)
    public void fixedDelayDaily() {
        long st = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long ed = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        log.info("计算每日时间->" + (ed - st));
    }

}
