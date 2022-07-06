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
@Service("kai_168")
public class Link168Impl implements GrabService {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    ResultInitService resultInitService;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    MqService mqService;

    /**
     * 抓取168结果
     *
     * @param linkUrl
     * @param lotteryKey
     */
    @Override
    public void linkData(String linkUrl, String lotteryKey) {
        try {
            String str = restTemplate.getForObject(linkUrl, String.class);
            //解析获取到的json数据
            JSONObject json = JSONObject.parseObject(str);
            int errorCode = Integer.parseInt(json.get("errorCode").toString());
            if (errorCode == 0) {
                JSONObject resultJson = JSONObject.parseObject(json.get("result").toString());
                int businessCode = Integer.parseInt(resultJson.get("businessCode").toString());
                if (businessCode == 0) {
                    JSONObject dataJson = JSONObject.parseObject(resultJson.get("data").toString());
                    String period = ResultDisposeUtil.disGameNo(lotteryKey, dataJson.get("preDrawIssue").toString());
                    String result = ResultDisposeUtil.disResult(lotteryKey, dataJson.get("preDrawCode").toString());
                    Integer i = resultInitService.saveInitResult(Constants.kai_168, lotteryKey, period, result);
                    if (i.equals(1)) {
                        log.info("168/{}/{}插入赛果成功！", lotteryKey, period);
                        mqService.sendMsg(Constants.kai_168, lotteryKey, period, result);
                    }
                }
            }
        } catch (Exception e) {
            log.info(lotteryKey + "==" + linkUrl + ",请求失败！！错误信息==" + e.getMessage());
        }
    }

    @Override
    public List<Map> linkHistoryData(String linkUrl, String lotteryKey) {
        try {
            String body = restTemplate.getForObject(linkUrl, String.class);
            if (StrUtil.isBlank(body)) {
                return null;
            }
            JSONObject json = JSONObject.parseObject(body);
            Integer errorCode = Integer.parseInt(json.get("errorCode").toString());
            if (errorCode.equals(0)) {
                JSONObject resultJson = JSONObject.parseObject(json.get("result").toString());
                Integer businessCode = Integer.parseInt(resultJson.get("businessCode").toString());
                if (businessCode.equals(0)) {
                    JSONArray jsonObject = JSONArray.parseArray(resultJson.get("data").toString());
                    List<Map> resMap = new ArrayList<>();
                    for (Object obj : jsonObject) {
                        JSONObject jjt = (JSONObject) obj;
                        String gameNo = ResultDisposeUtil.disGameNo(lotteryKey, jjt.get("preDrawIssue").toString());
                        String result = ResultDisposeUtil.disResult(lotteryKey, jjt.get("preDrawCode").toString());
                        Map map = new HashMap();
                        map.put("lotteryKey", lotteryKey);
                        map.put("gameNo", gameNo);
                        map.put("result", result);
                        map.put("link", Constants.kai_168 + "_history");
                        map.put("dateStr", jjt.get("preDrawTime").toString());
                        resMap.add(map);
                    }
                    return resMap;
                }
            }
        } catch (Exception e) {
            log.info(lotteryKey + "==" + linkUrl + ",历史请求失败！！错误信息==" + e.getMessage());
        }
        return null;
    }
}
