package com.xzy.gateway.logs;

import lombok.Data;

import java.util.Date;

@Data
public class GatewayLog {
    /*访问实例*/
    private String targetServer;
    /*请求路径*/
    private String requestPath;
    /*请求方法*/
    private String requestMethod;
    /*请求体*/
    private String requestBody;
    /*响应体*/
    private String responseData;
    /*请求ip*/
    private String ip;
    /*请求时间*/
    private Date requestTime;
    /*响应时间*/
    private Date responseTime;
    /*执行时间*/
    private Long executeTime;

    private String uid;
    private String username;
    private String loginInfo;
    private Date createDay;
}