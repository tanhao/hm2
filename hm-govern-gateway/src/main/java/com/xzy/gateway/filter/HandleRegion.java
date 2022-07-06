package com.xzy.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;
import java.util.function.Consumer;

public class HandleRegion {

    private static String regionKey="n-d-region";
    private static List<String> services = new ArrayList<String>(Arrays.asList(
            "hm2-service-user","hm2-service-ctrl","hm2-service-auth","hm2-service-result-receive",
            "hm2-service-logs","hm2-service-member","hm2-service-order"
    ));

    public static void checkRegion(ServerHttpRequest request,String active){
        List<String> strings = request.getHeaders().get(regionKey);
        //需要请求的服务
        Map<String ,String> region = new LinkedHashMap<>();
        services.stream().forEach(s -> region.put(s,active));
        if(null == strings || strings.size()==0){
            //没有设置region 参数，在这里追加
            buildRegion(request,JSON.toJSONString(region));
        }else {
            Map<String ,String> regionb = new LinkedHashMap<>();
            for (int i = 0; i < strings.size(); i++) {
                String keys= strings.get(i);
                JSONObject jsonObject = JSON.parseObject(keys);
                for(Map.Entry<String, String> entry : region.entrySet()){
                    String mapKey = entry.getKey();
                    if(null == jsonObject.get(mapKey)){
                        regionb.put(mapKey,active);
                    }else{
                        regionb.put(mapKey, (String) jsonObject.get(mapKey));
                    }
                }
            }
            buildRegion(request,JSON.toJSONString(regionb));
        }
    }

    /**
     * 覆盖请求头
     * @param request
     * @param regionVal
     */
    public static  void buildRegion(ServerHttpRequest request,String regionVal){
        HttpHeaders  regionHeaders= new HttpHeaders();
        MultiValueMap<String ,String> regionMap = new LinkedMultiValueMap<>();
        regionMap.add(regionKey,regionVal);
        regionMap.add("realServerName",request.getURI().getHost());
        regionMap.add("Cache-Control","no-cache,no-store,must-revalidate");
        regionMap.add("Pragma","no-cache");
        regionMap.add("Expires","0");
        regionHeaders.addAll(regionMap);
        Consumer<HttpHeaders> consumer = httpHeader -> httpHeader.addAll(regionHeaders);
        //TODO 这里要注意，如果n-d-region有值，构建后会产生集合数据，暂时没发现使用问题
        request.mutate().headers(consumer).build();
    }


}
