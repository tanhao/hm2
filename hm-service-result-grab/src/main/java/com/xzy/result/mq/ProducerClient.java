package com.xzy.result.mq;

import com.aliyun.openservices.ons.api.bean.OrderProducerBean;
import com.xzy.result.mq.config.MqConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProducerClient {

    @Autowired
    private MqConfig mqConfig;

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public OrderProducerBean buildOrderProducer() {
        OrderProducerBean orderProducerBean = new OrderProducerBean();
        orderProducerBean.setProperties(mqConfig.getMqPropertie());
        return orderProducerBean;
    }

}
