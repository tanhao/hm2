package com.xzy.user;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.xzy")
@EnableDubbo
@ComponentScan("com.xzy")
@SpringBootApplication
@EnableScheduling
public class UserApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(UserApplication.class).run(args);
    }


}
