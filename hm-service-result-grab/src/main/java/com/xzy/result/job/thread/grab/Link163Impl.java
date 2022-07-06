package com.xzy.result.job.thread.grab;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xzy.result.job.GrabService;
import com.xzy.result.mq.service.MqService;
import com.xzy.result.service.ResultInitService;
import com.xzy.result.util.Constants;
import com.xzy.result.util.RedisUtil;
import com.xzy.result.util.ResultDisposeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("kai_163")
public class Link163Impl implements GrabService {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    ResultInitService resultInitService;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    MqService mqService;

    @Override
    public void linkData(String linkUrl, String lotteryKey) {
        try {
            String str = restTemplate.getForObject(linkUrl, String.class);
            JSONObject json = JSONObject.parseObject(str);
            String resBool = json.get("success").toString();
            if (resBool == "true") {
                JSONObject dataJson = JSONObject.parseObject(json.get("result").toString());
                String period = dataJson.get("sgameperiod").toString();
                String result = dataJson.get("sopennum") != null ? dataJson.get("sopennum").toString() : null;
                if (result != null) {
                    result = result.replace("|", ",");
                    result = ResultDisposeUtil.disResultAddZero(lotteryKey, result);
                    Integer i = resultInitService.saveInitResult(Constants.kai_163, lotteryKey, period, result);
                    if (i.equals(1)) {
                        log.info("163/{}/{}插入赛果成功！", lotteryKey, period);
                        mqService.sendMsg(Constants.kai_163, lotteryKey, period, result);
                    }
                }
            }
        } catch (Exception e) {
            log.info(lotteryKey + "==" + linkUrl + ",请求失败！！错误信息==" + e.getMessage());
        }
    }

    @Override
    public List<Map> linkHistoryData(String linkUrl, String lotteryKey) {
        List<Map> retMap = new ArrayList<>();
        try {
            String body = restTemplate.getForObject(linkUrl, String.class);
            if (StrUtil.isBlank(body)) {
                return null;
            }
            JSONObject json = JSONObject.parseObject(body);
            String resBool = json.get("success").toString();
            if ("true".equals(resBool) && null != json.get("result")) {
                JSONArray jsonObject = JSONArray.parseArray(json.get("result").toString());
                for (Object obj : jsonObject) {
                    JSONObject jjt = (JSONObject) obj;
                    String gameNo = jjt.get("sgameperiod").toString();
                    String result = jjt.get("sopennum") != null ? jjt.get("sopennum").toString() : null;
                    if (result == null) continue;
                    result = ResultDisposeUtil.disResultAddZero(lotteryKey, result.replace("|", ","));
                    Map map = new HashMap();
                    map.put("lotteryKey", lotteryKey);
                    map.put("gameNo", gameNo);
                    map.put("result", result);
                    map.put("link", Constants.kai_163 + "_history");
                    map.put("dateStr", jjt.get("dopentime").toString());
                    retMap.add(map);
                }
                return retMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info(lotteryKey + "==" + linkUrl + ",历史请求失败！！错误信息==" + e.getMessage());
        }
        return null;
    }
}
