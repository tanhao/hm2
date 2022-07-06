package com.xzy.result.jobs;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.mq.http.MQClient;
import com.aliyun.mq.http.MQConsumer;
import com.aliyun.mq.http.common.AckMessageException;
import com.aliyun.mq.http.model.Message;
import com.xzy.pojo.result.ext.ResultGrab;
import com.xzy.result.ResultReceiveService;
import com.xzy.result.mq.config.MqConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ResultJobs {
    @Autowired
    private MqConfig mqConfig;
    @DubboReference
    ResultReceiveService resultReceiveService;

    @Scheduled(initialDelay = 1000, fixedRate = Long.MAX_VALUE)
    public void fixedDelayDaily() {
        MQClient mqClient = new MQClient(
                // 设置HTTP接入域名（此处以公共云生产环境为例）
                mqConfig.getHttpSrvAddr(),
                // AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
                mqConfig.getAccessKey(),
                // SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
                mqConfig.getSecretKey()
        );
        MQConsumer consumer = mqClient.getConsumer(mqConfig.getInstanceId(), mqConfig.getTopicResult(), mqConfig.getGroupResult(), "tag_result");
        // 在当前线程循环消费消息，建议是多开个几个线程并发消费消息。
        do {
            List<Message> messages = null;
            try {
                // 长轮询消费消息。
                // 长轮询表示如果 Topic 没有消息则请求会在服务端挂住 3s，3s 内如果有消息可以消费则立即返回。
                //  一次最多消费 3 条（最多可设置为 16 条）。
                // 长轮询时间 3 秒（最多可设置为 30 秒）。
                messages = consumer.consumeMessage(3, 1);
            } catch (Throwable e) {
                e.printStackTrace();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
            // 没有消息。
            if (messages == null || messages.isEmpty()) {
                //log.info(Thread.currentThread().getName() + ": no new message, continue!");
                continue;
            }
            // 处理业务逻辑。
            for (Message message : messages) {
                try {
                    ResultGrab resultGrab = JSONObject.parseObject(new String(message.getMessageBodyBytes()), ResultGrab.class);
                    resultGrab.setUpdateBy("socket_mq_jobs");
                    resultGrab.setAutoPayout(Boolean.FALSE);
                    //先进行保存赛果操作
                    Integer i = resultReceiveService.saveResult(resultGrab);
                    if (i.equals(200)) {
                        resultReceiveService.sendResult(resultGrab);
                        resultReceiveService.spPayoutPrize(resultGrab);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // Message.nextConsumeTime 前若不确认消息消费成功，则消息会重复消费。
            // 消息句柄有时间戳，同一条消息每次消费拿到的都不一样。
            {
                List<String> handles = new ArrayList<String>();
                for (Message message : messages) {
                    handles.add(message.getReceiptHandle());
                }
                try {
                    consumer.ackMessage(handles);
                } catch (Throwable e) {
                    // 某些消息的句柄可能超时了会导致确认不成功。
                    if (e instanceof AckMessageException) {
                        AckMessageException errors = (AckMessageException) e;
                        System.out.println("Ack message fail, requestId is:" + errors.getRequestId() + ", fail handles:");
                        if (errors.getErrorMessages() != null) {
                            for (String errorHandle : errors.getErrorMessages().keySet()) {
                                System.out.println("Handle:" + errorHandle + ", ErrorCode:" + errors.getErrorMessages().get(errorHandle).getErrorCode()
                                        + ", ErrorMsg:" + errors.getErrorMessages().get(errorHandle).getErrorMessage());
                            }
                        }
                        continue;
                    }
                    e.printStackTrace();
                }
            }
        } while (true);
    }
}
