package com.xzy.smart;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.xzy.smart.dao")
@EnableDubbo
@ComponentScan("com.xzy")
@SpringBootApplication
@EnableScheduling
@EnableAsync
public class SmartApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SmartApplication.class).run(args);
    }

}
