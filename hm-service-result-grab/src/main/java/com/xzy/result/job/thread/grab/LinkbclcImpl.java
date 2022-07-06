package com.xzy.result.job.thread.grab;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.shaded.com.google.common.base.Joiner;
import com.xzy.result.config.ProxyCustomizer;
import com.xzy.result.job.GrabService;
import com.xzy.result.mq.service.MqService;
import com.xzy.result.service.ResultInitService;
import com.xzy.result.util.Constants;
import com.xzy.result.util.DateUtil;
import com.xzy.result.util.RedisUtil;
import com.xzy.result.util.ResultDisposeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service("kai_bclc")
public class LinkbclcImpl implements GrabService {
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
            RestTemplate restTemplate = ProxyCustomizer.customize();
            String body = restTemplate.getForObject(linkUrl, String.class);
            if (StrUtil.isNotBlank(body)) {
                JSONObject json = JSONObject.parseObject(body);
                Integer bonus = json.getInteger("bonus");
                //如果bonus》0说明有赛果了
                if (bonus > 0) {
                    List<Integer> num = json.getJSONArray("num").toJavaList(Integer.class);
                    Collections.sort(num);
                    String period = json.getString("draw");
                    Date drawDate = (Date) json.getTimestamp("drawDate");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Calendar nowTime = Calendar.getInstance();
                    nowTime.setTime(drawDate);
                    nowTime.add(Calendar.HOUR, 15);
                    if (num.size() > 0) {
                        String result = ResultDisposeUtil.disResultAddZero(lotteryKey, Joiner.on(",").join(num));
                        Integer i = resultInitService.saveInitResult(Constants.kai_bclc, lotteryKey, period, result);
                        if (i.equals(1)) {
                            log.info("bclc/{}/{}插入赛果成功！", lotteryKey, period);
                            mqService.sendMsg(Constants.kai_bclc, lotteryKey, period, result);
                            if (lotteryKey.equals(Constants.lotteryName.KENO.getKey())) {
                                //计算JND28赛果
                                Integer num1 = num.get(1) + num.get(4) + num.get(7) + num.get(10) + num.get(13) + num.get(16);
                                Integer num2 = num.get(2) + num.get(5) + num.get(8) + num.get(11) + num.get(14) + num.get(17);
                                Integer num3 = num.get(3) + num.get(6) + num.get(9) + num.get(12) + num.get(15) + num.get(18);
                                String result28 = num1.toString().substring(num1.toString().length() - 1)
                                        + "," + num2.toString().substring(num2.toString().length() - 1)
                                        + "," + num3.toString().substring(num3.toString().length() - 1);
                                String key28 = Constants.lotteryName.JND28.getKey();
                                Integer ii = resultInitService.saveInitResult(Constants.kai_bclc, key28, period, result28);
                                if (ii.equals(1)) {
                                    log.info("bclc/{}/{}插入赛果成功！", key28, period);
                                    mqService.sendMsg(Constants.kai_bclc, key28, period, result28);
                                }
                            }
                            //检查keno是否需要生成期号
                            resultInitService.checkFastGameNo(period, sdf.format(nowTime.getTime()));
                        }
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
            RestTemplate restTemplate = ProxyCustomizer.customize();
            String body = restTemplate.getForObject(linkUrl, String.class);
            if (StrUtil.isBlank(body)) {
                return null;
            }
            JSONArray jsonArray = JSONArray.parseArray(body);
            for (Object obj : jsonArray) {
                JSONObject jjt = (JSONObject) obj;
                String gameNo = jjt.get("drawNbr").toString();
                List<Integer> num = jjt.getJSONArray("drawNbrs").toJavaList(Integer.class);
                Collections.sort(num);
                if (num == null) continue;
                Map map = new HashMap();
                map.put("lotteryKey", lotteryKey);
                map.put("gameNo", gameNo);
                map.put("result", Joiner.on(",").join(num));
                map.put("link", Constants.kai_bclc + "_history");
                String drawDate = DateUtil.canadaDate(jjt.get("drawDate").toString(), jjt.get("drawTime").toString());
                map.put("dateStr", drawDate);
                //计算JND28赛果
                Integer num1 = num.get(1) + num.get(4) + num.get(7) + num.get(10) + num.get(13) + num.get(16);
                Integer num2 = num.get(2) + num.get(5) + num.get(8) + num.get(11) + num.get(14) + num.get(17);
                Integer num3 = num.get(3) + num.get(6) + num.get(9) + num.get(12) + num.get(15) + num.get(18);
                String result28 = num1.toString().substring(num1.toString().length() - 1)
                        + "," + num2.toString().substring(num2.toString().length() - 1)
                        + "," + num3.toString().substring(num3.toString().length() - 1);
                map.put("result28", result28);
                retMap.add(map);
            }
            return retMap;
        } catch (Exception e) {
            e.printStackTrace();
            log.info(lotteryKey + "==" + linkUrl + ",历史请求失败！！错误信息==" + e.getMessage());
        }
        return null;
    }
}
