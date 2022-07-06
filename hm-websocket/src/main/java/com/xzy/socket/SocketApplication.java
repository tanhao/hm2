package com.xzy.socket;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.tio.websocket.starter.EnableTioWebSocketServer;

@ComponentScan("com.xzy")
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableTioWebSocketServer
public class SocketApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SocketApplication.class).run(args);
    }

}
