package com.xzy.gateway.logs;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.xzy.gateway.logs.dao.GatewayLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class GatewayLogServer {
    @Autowired
    GatewayLogMapper gatewayLogMapper;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    //查询令牌的有效个数
    private String getTokenSize(String uid) {
        //通配符key
        String key = "hm2_token:" + uid + ":*";
        //查询所有key
        Set<String> keys = stringRedisTemplate.keys(key);
        if (keys.size() == 0) {
            return null;
        }
        return stringRedisTemplate.opsForValue().get(keys.iterator().next());
    }

    @Async
    public void insertSelective(GatewayLog gatewayLog) {
        try {
            String value = getTokenSize(gatewayLog.getUid());
            if (StrUtil.isNotBlank(value)) {
                JSONObject parse = JSONObject.parseObject(value);
                Object username = parse.get("username");
                if (ObjectUtil.isNotNull(username)) {
                    gatewayLog.setUsername(username.toString());
                }
                gatewayLog.setLoginInfo(parse.toJSONString());
            }
            if (StrUtil.isNotBlank(gatewayLog.getResponseData()) && gatewayLog.getResponseData().length() > 1000) {
                gatewayLog.setResponseData(gatewayLog.getResponseData().substring(0, 1000));
            }
            gatewayLogMapper.insertSelective(gatewayLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
