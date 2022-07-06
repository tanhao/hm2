package com.xzy.mem;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.xzy")
@EnableDubbo
@ComponentScan("com.xzy")
@SpringBootApplication
@EnableAsync
public class MemApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(MemApplication.class).run(args);
    }


}
