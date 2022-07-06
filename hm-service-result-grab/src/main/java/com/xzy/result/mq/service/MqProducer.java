package com.xzy.result.mq.service;

import com.aliyun.mq.http.MQClient;
import com.aliyun.mq.http.MQProducer;
import com.aliyun.mq.http.model.TopicMessage;
import com.xzy.result.mq.config.MqConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class MqProducer {
    @Autowired
    private MqConfig mqConfig;

    public void sendMsg(String msg) {
        MQClient mqClient = new MQClient(
                // 设置HTTP接入域名（此处以公共云生产环境为例）
                mqConfig.getHttpSrvAddr(),
                // AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
                mqConfig.getAccessKey(),
                // SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
                mqConfig.getSecretKey()
        );
        String topic = mqConfig.getTopicResult();
        // 您在控制台创建的 Group ID
        // Topic所属实例ID，默认实例为空
       String instanceId =  mqConfig.getInstanceId();
        // 获取Topic的生产者
        MQProducer  producer = mqClient.getProducer(instanceId, topic);
        try {
            TopicMessage pubMsg = new TopicMessage(
                    // 消息内容
                    msg.getBytes(),
                    // 消息标签
                    "tag_result"
            );
            TopicMessage pubResultMsg = producer.publishMessage(pubMsg);
            log.info("输出msgId={}结果={}", pubResultMsg.getMessageId(),msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mqClient.close();
    }

}
