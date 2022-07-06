package com.xzy.trade.conntrol;


import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.order.OrderProducer;
import com.xzy.trade.config.MqConfig;
import com.xzy.trade.order.OrderProducerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class OrderCtr {
    @Autowired
    private OrderProducerClient orderProducerClient;
    @Autowired
    private MqConfig mqConfig;

    @RequestMapping("/order")
    public void orderSave(){
        OrderProducer producer = orderProducerClient.buildOrderProducer();
        for (int i = 0; i < 10; i++) {
            String orderId = "biz_" + i % 10;
            Long time = System.currentTimeMillis();
            Message msg = new Message(mqConfig.getShardingTopic(),"Tag_sharding",("send Tag_sharding "+time).getBytes());
            // 设置代表消息的业务关键属性，请尽可能全局唯一。
            // 以方便您在无法正常收到消息情况下，可通过控制台查询消息并补发。
            // 注意：不设置也不会影响消息正常收发
            msg.setKey(orderId);
            // 分区顺序消息中区分不同分区的关键字段，Sharding Key 与普通消息的 key 是完全不同的概念。
            // 全局顺序消息，该字段可以设置为任意非空字符串。
            String shardingKey = String.valueOf(orderId);
            try {
                SendResult sendResult = producer.send(msg, shardingKey);
                // 发送消息，只要不抛异常就是成功
                if (sendResult != null) {
                    System.out.println(time + " Send mq message success. Topic is:" + msg.getTopic() + " msgId is: " + sendResult.getMessageId());
                }
            }catch (Exception e) {
                // 消息发送失败，需要进行重试处理，可重新发送这条消息或持久化这条数据进行补偿处理
                System.out.println(time + " Send mq message failed. Topic is:" + msg.getTopic());
                e.printStackTrace();
            }
        }
    }
}
