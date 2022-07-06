package com.xzy.result.job.thread;

import com.xzy.result.job.GrabService;
import com.xzy.result.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
@Slf4j
public class Linkbclc {
    @Autowired
    private ApplicationContext applicationContext;
    private static Map map = new HashMap();

    static {
        map.put(Constants.lotteryName.KENO.getKey(), "https://www.playnow.com/services2/keno/nextdraw/?_=");
    }

    @Scheduled(fixedDelay = 5000)
    public void runLink() {
        try {
            for (Object key : map.keySet()) {
                String url = (String) map.get(key)+System.currentTimeMillis();
                GrabService grabService = applicationContext.getBean(Constants.kai_bclc, GrabService.class);
                grabService.linkData(url, key.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
