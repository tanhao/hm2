package com.xzy.ctrl.service.impl;

import java.util.Collections;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class RedisLock {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisTemplate redisTemplate;

    // 加锁lua脚本
    private static final String LOCK_LUA = "if redis.call('setNx',KEYS[1],ARGV[1]) then if redis.call('get',KEYS[1])==ARGV[1] then return redis.call('expire',KEYS[1],ARGV[2]) else return 0 end end";
    // 解锁lua脚本
    private static final String UNLOCK_LUA = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
    // 过期时间30秒
    private static Integer EXPIRE_TIME = 30;

    private static final Long SUCCESS = 1L;

    public boolean lock(String lockKey, String lockValue) {
       return this.lock(lockKey, lockValue, EXPIRE_TIME);
    }

    private boolean lock(String lockKey, String lockValue, int expireTime) {
        RedisScript<Long> redisScript = new DefaultRedisScript<>(LOCK_LUA, Long.class);
        Object result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey), lockValue, expireTime);
        if (SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }

    public boolean unlock(String lockKey, String lockValue) {
        RedisScript<Long> redisScript = new DefaultRedisScript<>(UNLOCK_LUA, Long.class);
        Object result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey), lockValue);
        if (SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }

}