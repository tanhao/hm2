package com.xzy.grab.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class Start {
    @PostConstruct
    public void start() {

        try {
//            RestTemplate restTemplate = RestTemplateUtil.getInstance("utf-8");
            String linkUrl = "https://api.apiose122.com/klsf/getLotteryInfo.do?lotCode=10005";
            String str = HttpUtil.getByHttpClientWithChrome(linkUrl);
//            String str = restTemplate.getForObject(linkUrl, String.class);
            log.info(str);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
