package com.xzy.ctrl;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.xzy")
@ComponentScan("com.xzy")
@EnableDubbo
@SpringBootApplication
public class CtrlApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(CtrlApplication.class).run(args);
    }

}
