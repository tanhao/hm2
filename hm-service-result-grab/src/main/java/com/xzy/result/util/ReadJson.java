package com.xzy.result.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class ReadJson {

    private static Resource json168;

    @Value("classpath:static/json/http168history.json")
    public void setJson168(Resource json){
        ReadJson.json168 = json;
    }

    private static Resource json163;

    @Value("classpath:static/json/http163history.json")
    public void setJson163(Resource json){
        ReadJson.json163 = json;
    }

    private static Resource jsonbclc;
    @Value("classpath:static/json/httpbclchistory.json")
    public void setJsonbclc(Resource json){
        ReadJson.jsonbclc = json;
    }


    public static JSONObject getLink168Json(){
        try {
            String areaData = IOUtils.toString(json168.getInputStream(), "UTF-8");
            return JSONObject.parseObject(areaData);
        } catch (IOException e) {
            log.error("",e);
        }
        return null;
    }

    public static JSONObject getLink163Json(){
        try {
            String areaData = IOUtils.toString(json163.getInputStream(), "UTF-8");
            return JSONObject.parseObject(areaData);
        } catch (IOException e) {
            log.error("",e);
        }
        return null;
    }
    public static JSONObject getLinkbclcJson(){
        try {
            String areaData = IOUtils.toString(jsonbclc.getInputStream(), "UTF-8");
            return JSONObject.parseObject(areaData);
        } catch (IOException e) {
            log.error("",e);
        }
        return null;
    }

}
