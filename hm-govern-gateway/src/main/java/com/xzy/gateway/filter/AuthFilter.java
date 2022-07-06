package com.xzy.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.xzy.gateway.logs.IpUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class AuthFilter implements GlobalFilter, Ordered {
    private static final Logger log = LoggerFactory.getLogger(AuthFilter.class);
    int expire = 3600;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String stop = stringRedisTemplate.opsForValue().get("hm2_system_stop");
        if ("1".equals(stop)) {
            String sys = exchange.getRequest().getHeaders().getFirst("sys");
            if (!"sys".equals(sys)) {
                return accessDenied(exchange.getResponse(), "系统维护，稍后在试！");
            }
        }
        List<String> ban = stringRedisTemplate.opsForList().range("hm2_system_ban", 0, -1);
        String ipAddress = IpUtil.getIpAddress(request);
        if (ban != null && ban.contains(ipAddress)) {
            return accessDenied(exchange.getResponse(), "禁止访问！");
        }
        String path = exchange.getRequest().getPath().toString();
        //过滤拦截列表
        String[] ignores = {
                "/v2/auth/login",
                "/v2/auth/logout",
                "/v2/auth/captcha",
                "/v2/ucenter/website/siteCode",
                "/v2/ucenter/search/code",
                "/v2/ucenter/user/updatePwd/notoken",
        };
        //过滤拦截请求
        for (int i = 0; i < ignores.length; i++) {
            if (path.contains(ignores[i])) {
                return chain.filter(exchange);
            }
        }
        String uid = exchange.getRequest().getHeaders().getFirst("uid");
        //返回401状态码和提示信息
        if (StringUtils.isBlank(uid)) {
            return accessDenied(exchange.getResponse(), "此操作需要登陆系统！");
        }
        String token = this.getTokenSize(uid);
        if (null == token) {
            //拒绝访问
            return accessDenied(exchange.getResponse(), "此操作需要登陆系统！");
        }
        String value = stringRedisTemplate.opsForValue().get(token);
        JSONObject parse = JSONObject.parseObject(value);
        Object primaryId = parse.get("primaryId");
        if (primaryId.equals(1)) {
            if (!path.equals("/v2/ucenter/online/onlineNum") && !path.equals("/v2/ctrl/notice-show") && !path.equals("now-lottery")) {
                Object username = parse.get("username");
                log.info("后桶账号{},请求{},{}方法", username, exchange.getRequest().getMethod(), path);
            }
        }
        return chain.filter(exchange);
    }

    //查询令牌的有效个数
    private String getTokenSize(String uid) {
        //通配符key
        String key = "hm2_token:" + uid + ":*";
        //查询所有key
        Set<String> keys = stringRedisTemplate.keys(key);
        if (keys.size() == 0) {
            return null;
        }
        String token = keys.iterator().next();
        //刷新超时时间
        stringRedisTemplate.expire(token, expire, TimeUnit.SECONDS);
        return token;
    }

    //拒绝响应结果
    private Mono<Void> accessDenied(ServerHttpResponse response, String msg) {
        JSONObject message = new JSONObject();
        message.put("success", false);
        message.put("message", msg);
        message.put("code", 10001);
        //10001,"此操作需要登陆系统！"
        byte[] bits = message.toJSONString().getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        //指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
        response.setStatusCode(HttpStatus.OK);
        return response.writeWith(Mono.just(buffer));
    }

    //数值越低越优先执行
    @Override
    public int getOrder() {
        return -20;
    }
}