package com.xzy.trade.sharding;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.bean.OrderConsumerBean;
import com.aliyun.openservices.ons.api.bean.Subscription;
import com.aliyun.openservices.ons.api.order.ConsumeOrderContext;
import com.aliyun.openservices.ons.api.order.MessageOrderListener;
import com.aliyun.openservices.ons.api.order.OrderAction;
import com.aliyun.openservices.ons.api.order.OrderConsumer;
import com.xzy.trade.config.MqConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class ShardingConsumerClient {
    @Autowired
    private MqConfig mqConfig;
    @Autowired
    private ShardingMessageListener shardingMessageListener;

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public OrderConsumerBean buildOShardingConsumer() {
        OrderConsumerBean orderConsumerBean = new OrderConsumerBean();
        //配置文件
        Properties properties = mqConfig.getMqPropertie();
        properties.setProperty(PropertyKeyConst.GROUP_ID, mqConfig.getShardingGroupId());
        orderConsumerBean.setProperties(properties);
        //订阅关系
        Map<Subscription, MessageOrderListener> subscriptionTable = new HashMap<Subscription, MessageOrderListener>();
        Subscription subscription = new Subscription();
        subscription.setTopic(mqConfig.getShardingTopic());
        subscription.setExpression(mqConfig.getShardingTag());
        subscriptionTable.put(subscription, shardingMessageListener);
        //订阅多个topic如上面设置
        orderConsumerBean.setSubscriptionTable(subscriptionTable);
        return orderConsumerBean;
    }
}