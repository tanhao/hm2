package com.xzy.result.config;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;

public class ProxyCustomizer {
    public static String PROXY_SERVER_HOST = "54.177.161.165";
    public static int PROXY_SERVER_PORT = 13128;


    public static RestTemplate customize() {
        RestTemplate restTemplate = new RestTemplate();
        SimpleClientHttpRequestFactory reqfac = new SimpleClientHttpRequestFactory();
        reqfac.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(PROXY_SERVER_HOST, PROXY_SERVER_PORT)));
        reqfac.setReadTimeout(120000);
        reqfac.setConnectTimeout(10000);// 指客户端和服务器建立连接的timeout
        restTemplate.setRequestFactory(reqfac);
        return restTemplate;
    }
}
