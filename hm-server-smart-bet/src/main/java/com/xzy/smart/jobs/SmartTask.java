package com.xzy.smart.jobs;

import com.xzy.smart.service.SmartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@EnableAsync
public class SmartTask {
    @Autowired
    SmartService smartService;

    @Scheduled(fixedDelay = 5000)
    public void fixedDelayDaily() {
        smartService.taskSmart();
    }
}
