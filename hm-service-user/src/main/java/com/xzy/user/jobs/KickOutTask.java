package com.xzy.user.jobs;

import com.xzy.utils.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KickOutTask {
    @Autowired
    LoginUtil loginUtil;

    @Scheduled(cron = "0 0 6 * * ?")
    public void fixedDelayDaily() {
        loginUtil.delAll();
        log.info("踢出所有账号");
    }
}
