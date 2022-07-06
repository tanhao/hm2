package com.xzy.trade.sharding;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.order.ConsumeOrderContext;
import com.aliyun.openservices.ons.api.order.MessageOrderListener;
import com.aliyun.openservices.ons.api.order.OrderAction;
import org.springframework.stereotype.Component;

@Component
public class ShardingMessageListener implements MessageOrderListener {
    @Override
    public OrderAction consume(final Message message, final ConsumeOrderContext context) {
        System.out.println("Sharding: " + message);
        try {
            System.out.println(message.getTag());
            System.out.println(message.getShardingKey());
            System.out.println(new String(message.getBody())+"???");
            //do something..
            return OrderAction.Success;
        } catch (Exception e) {
            //消费失败，挂起当前队列
            return OrderAction.Suspend;
        }
    }
}
