package com.xzy.result.mq.service;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Message;
import com.xzy.result.mq.ProducerClient;
import com.xzy.result.mq.config.MqConfig;
import com.xzy.result.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
//@ConditionalOnProperty(prefix = "rocketmq", name = "start", havingValue = "true")
@Slf4j
public class MqService {
    @Autowired
    private ProducerClient producerClient;
    @Autowired
    private MqConfig mqConfig;
    @Autowired
    private MqProducer mqProducer;
    @Autowired
    RedisUtil redisUtil;

    public void sendMsg(String https, String lotteryKey, String period, String result) {
        try {
            String mapKey = StrUtil.format("{}-{}-{}-{}", https, lotteryKey, period, redisUtil.IPkey);
            JSONObject json = new JSONObject();
            json.put("lotteryKey", lotteryKey);
            json.put("gameNo", period);
            json.put("result", result);
            json.put("https", https);
            String parseObject = json.toString();
            Message message = new Message(mqConfig.getTopicResult(), "tag_result", parseObject.getBytes());
            //发送
            String shardingKey = String.valueOf(period);
//            SendResult sendResult = producerClient.buildOrderProducer().send(message, shardingKey);
            //log.info("输出赛果={}", parseObject);
            mqProducer.sendMsg(parseObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
