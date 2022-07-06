package com.xzy.config;

import org.apache.dubbo.config.ApplicationConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AutoComponent {
    @Bean
    public ApplicationConfig applicationConfig(@Value("${spring.application.name}") String name){
        return new ApplicationConfig(name);
    }
}
