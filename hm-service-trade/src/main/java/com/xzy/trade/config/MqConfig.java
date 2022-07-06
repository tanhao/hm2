package com.xzy.trade.config;

import com.aliyun.openservices.ons.api.PropertyKeyConst;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
@ConfigurationProperties(prefix = "rocketmq")
@Data
public class MqConfig {
    private String accessKey="LTAI4GKUsfWhDnq9osUgHJ4M";
    private String secretKey="xJgEdrG7ydWqPDLcYj4Cg8lhjpxSY7";
    private String nameSrvAddr="http://MQ_INST_1458397360839400_BcqwRMNA.mq-internet-access.mq-internet.aliyuncs.com:80";
    private String topic="topic_trade";
    private String groupId="GID_trade";
    private String tag="*";

    private String orderTopic="topic_tran_trade";
    private String orderGroupId="GID_tran_trade";
    private String orderTag="*";

    private String shardingTopic="topic_sharding";
    private String shardingGroupId="GID_sharding";
    private String shardingTag="*";

    public Properties getMqPropertie() {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.AccessKey, this.accessKey);
        properties.setProperty(PropertyKeyConst.SecretKey, this.secretKey);
        properties.setProperty(PropertyKeyConst.NAMESRV_ADDR, this.nameSrvAddr);
        return properties;
    }

}
