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
public class Link168 {
    @Autowired
    private ApplicationContext applicationContext;
    private static Map map = new HashMap();

    static {
        //map.put(Constants.lotteryName.BJPK10.getKey(),"https://api.apiose188.com/pks/getLotteryPksInfo.do?lotCode=10001");
        //map.put(Constants.lotteryName.XYNC.getKey(),"https://api.apiose188.com/klsf/getLotteryInfo.do?lotCode=10009");
        //map.put(Constants.lotteryName.GDKL10.getKey(),"https://api.apiose188.com/klsf/getLotteryInfo.do?lotCode=10005");
        //map.put(Constants.lotteryName.CQSSC.getKey(),"https://api.apiose188.com/CQShiCai/getBaseCQShiCai.do?lotCode=10060");
        //map.put(Constants.lotteryName.XJSSC.getKey(),"https://api.apiose188.com/CQShiCai/getBaseCQShiCai.do?lotCode=10004");

        map.put(Constants.lotteryName.SPEED10.getKey(), "https://api.apiose188.com/pks/getLotteryPksInfo.do?lotCode=10037");
        map.put(Constants.lotteryName.XYFT.getKey(), "https://api.apiose188.com/pks/getLotteryPksInfo.do?lotCode=10057");
        map.put(Constants.lotteryName.LUCKY5.getKey(), "https://api.apiose188.com/CQShiCai/getBaseCQShiCai.do?lotCode=10010");
        map.put(Constants.lotteryName.LUCKY8.getKey(), "https://api.apiose188.com/klsf/getLotteryInfo.do?lotCode=10011");
        map.put(Constants.lotteryName.LUCKY10.getKey(), "https://api.apiose188.com/pks/getLotteryPksInfo.do?lotCode=10012");
        map.put(Constants.lotteryName.SBSPEED10.getKey(), "https://api.apiose188.com/pks/getLotteryPksInfo.do?lotCode=10035");
        map.put(Constants.lotteryName.SPEED8.getKey(), "https://api.apiose188.com/klsf/getLotteryInfo.do?lotCode=10053");
        map.put(Constants.lotteryName.JSSSC.getKey(), "https://api.apiose188.com/CQShiCai/getBaseCQShiCai.do?lotCode=10036");
        map.put(Constants.lotteryName.JSDD.getKey(), "https://api.apiose188.com/LuckTwenty/getPcLucky28.do?&lotCode=10081");
        map.put(Constants.lotteryName.PCDD.getKey(), "https://api.apiose188.com/LuckTwenty/getPcLucky28.do?&lotCode=10074");
        map.put(Constants.lotteryName.LUCKY20.getKey(), "https://api.apiose188.com/LuckTwenty/getBaseLuckTewnty.do?lotCode=10013");
        map.put(Constants.lotteryName.JSKL8.getKey(), "https://api.apiose188.com/LuckTwenty/getBaseLuckTewnty.do?lotCode=10054");
    }

    @Scheduled(fixedDelay = 5000)
    public void runLink() {
        try {
            for (Object key : map.keySet()) {
                String url = (String) map.get(key);
                GrabService grabService = applicationContext.getBean(Constants.kai_168, GrabService.class);
                grabService.linkData(url, key.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
