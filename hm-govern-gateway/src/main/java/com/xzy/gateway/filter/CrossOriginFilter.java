package com.xzy.gateway.filter;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

@Component
public class CrossOriginFilter implements GlobalFilter, Ordered {
    @Value("${spring.profiles.active}")
    String active;

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, GatewayFilterChain chain) {
        ServerHttpRequest request = serverWebExchange.getRequest();
        HandleRegion.checkRegion(request,active);
        if (!CorsUtils.isCorsRequest(request)) {
            return chain.filter(serverWebExchange);
        }
        ServerHttpResponse response = serverWebExchange.getResponse();
        if (request.getMethod() == HttpMethod.OPTIONS) {
            response.setStatusCode(HttpStatus.OK);
            return Mono.empty();
        }
        response.getHeaders().add("Cache-Control","no-cache,no-store,must-revalidate");
        response.getHeaders().add("Pragma","no-cache");
        response.getHeaders().add("Expires","0");
        ////////重新发送数据,更改Origin为*
        DataBufferFactory bufferFactory = response.bufferFactory();
        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(response) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                    return super.writeWith(fluxBody.map(dataBuffer -> {
                        byte[] content = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(content);
                        DataBufferUtils.release(dataBuffer);
                        if(null != response.getHeaders()){
                            if(!("*").equals(response.getHeaders().getAccessControlAllowOrigin())){
                                response.getHeaders().setAccessControlAllowOrigin("*");
                            }
                        }
                        byte[] uppedContent = new String(content, Charset.forName("UTF-8")).getBytes();
                        return bufferFactory.wrap(uppedContent);
                    }));
                }
                return super.writeWith(body);
            }
        };
        return chain.filter(serverWebExchange.mutate().response(decoratedResponse).build());
        //return chain.filter(serverWebExchange);
    }

    @Override
    public int getOrder() {
        return -30;
    }
}