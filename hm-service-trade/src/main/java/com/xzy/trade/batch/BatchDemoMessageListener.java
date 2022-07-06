package com.xzy.trade.batch;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.batch.BatchMessageListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BatchDemoMessageListener implements BatchMessageListener {

    @Override
    public Action consume(final List<Message> messages, final ConsumeContext context) {
        System.out.println("Receive batch: " + messages.size() + " messages");
        for (Message message : messages) {
            System.out.println(new String(message.getBody()));
        }
        try {
            //do something..
            return Action.CommitMessage;
        } catch (Exception e) {
            e.printStackTrace();
            //消费失败
            return Action.ReconsumeLater;
        }
    }
}
