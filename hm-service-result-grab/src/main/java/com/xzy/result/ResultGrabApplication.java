package com.xzy.result;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.xzy.result.dao")
@SpringBootApplication
@EnableScheduling
@EnableAsync
public class ResultGrabApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(ResultGrabApplication.class).run(args);
    }
}
