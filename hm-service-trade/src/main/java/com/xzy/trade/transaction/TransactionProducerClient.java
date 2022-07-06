package com.xzy.trade.transaction;

import com.aliyun.openservices.ons.api.bean.TransactionProducerBean;
import com.xzy.trade.config.MqConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionProducerClient {

    @Autowired
    private MqConfig mqConfig;

    @Autowired
    private DemoLocalTransactionChecker localTransactionChecker;

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public TransactionProducerBean buildTransactionProducer() {
        TransactionProducerBean producer = new TransactionProducerBean();
        producer.setProperties(mqConfig.getMqPropertie());
        producer.setLocalTransactionChecker(localTransactionChecker);
        return producer;
    }

}
