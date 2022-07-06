package com.xzy.logs.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class IPUtil {
    @Autowired
    RestTemplate restTemplate;

    public String getIpRegion(String ip) {
        try {
            String host = "https://hcapi20.market.alicloudapi.com";
            String path = "/ip";
            String appcode = "e301c1f36e2d490b99c6717915062efb";
            String url = String.join("", host, path, "?ip=", ip);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "APPCODE " + appcode);
            HttpEntity<MultiValueMap> requestEntity = new HttpEntity<>(null, headers);
            log.info(url);
            ResponseEntity<String> forEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
            String body = forEntity.getBody();
            JSONObject res = JSONObject.parseObject(body);
            if (res.get("ret").equals(200)) {
                JSONObject data = JSONObject.parseObject(res.get("data").toString());
                return data.get("country").toString()+data.get("region") + data.get("city")+" "+data.get("isp") ;
            }else{
                return res.get("msg").toString();
            }
        } catch (HttpClientErrorException e) {
            String body = e.getResponseBodyAsString();
            log.info(body);
            JSONObject res = JSONObject.parseObject(body);
            return res.get("msg").toString();
        }
    }

}
