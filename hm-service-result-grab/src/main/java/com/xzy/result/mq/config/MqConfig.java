package com.xzy.result.mq.config;

import com.aliyun.openservices.ons.api.PropertyKeyConst;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
@ConfigurationProperties(prefix = "rocketmq")
@Data
public class MqConfig {
    private String instanceId;
    private String accessKey;
    private String secretKey;
    private String nameSrvAddr;
    private String httpSrvAddr;
    private String topicResult;
    private String groupResult;
    private String tagResult="*";

    public Properties getMqPropertie() {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.AccessKey, this.accessKey);
        properties.setProperty(PropertyKeyConst.SecretKey, this.secretKey);
        properties.setProperty(PropertyKeyConst.NAMESRV_ADDR, this.nameSrvAddr);
        properties.setProperty(PropertyKeyConst.ConsumeThreadNums,"20");
        return properties;
    }

}
