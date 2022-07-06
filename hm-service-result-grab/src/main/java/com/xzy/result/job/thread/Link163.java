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
public class Link163 {
    @Autowired
    private ApplicationContext applicationContext;
    private static Map map = new HashMap();

    static {
        //map.put(Constants.lotteryName.BJPK10.getKey(),"https://www.1632026.com/api/homePage/gameNewDataForLotteryHall?iGameId=9");
        //map.put(Constants.lotteryName.GDKL10.getKey(),"https://www.1632026.com/api/homePage/gameNewDataForLotteryHall?iGameId=27");
        //map.put(Constants.lotteryName.CQSSC.getKey(),"https://www.1632026.com/api/homePage/gameNewDataForLotteryHall?iGameId=5");
        //map.put(Constants.lotteryName.XJSSC.getKey(),"https://www.1632026.com/api/homePage/gameNewDataForLotteryHall?iGameId=7");

        map.put(Constants.lotteryName.SPEED10.getKey(), "https://www.1632026.com/api/homePage/gameNewDataForLotteryHall?iGameId=56");
        map.put(Constants.lotteryName.JSSSC.getKey(), "https://www.1632026.com/api/homePage/gameNewDataForLotteryHall?iGameId=55");
        map.put(Constants.lotteryName.XYFT.getKey(), "https://www.1632026.com/api/homePage/gameNewDataForLotteryHall?iGameId=38");
        map.put(Constants.lotteryName.LUCKY5.getKey(), "https://www.1632026.com/api/homePage/gameNewDataForLotteryHall?iGameId=31");
        map.put(Constants.lotteryName.LUCKY10.getKey(), "https://www.1632026.com/api/homePage/gameNewDataForLotteryHall?iGameId=33");
        map.put(Constants.lotteryName.SPEED8.getKey(), "https://www.1632026.com/api/homePage/gameNewDataForLotteryHall?iGameId=66");
        map.put(Constants.lotteryName.LUCKY20.getKey(), "https://www.1632026.com/api/homePage/gameNewDataForLotteryHall?iGameId=34");
    }

    @Scheduled(fixedDelay = 5000)
    public void runLink() {
        try {
            for (Object key : map.keySet()) {
                String url = (String) map.get(key);
                GrabService grabService = applicationContext.getBean(Constants.kai_163, GrabService.class);
                grabService.linkData(url, key.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
