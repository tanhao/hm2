package com.xzy.result.util;


import cn.hutool.core.util.ObjectUtil;
import com.xzy.result.util.IPGetUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 */
@Component
@Slf4j
//@Order(1)
public class RedisUtil {
    @Autowired
    RedisTemplate redisTemplate;
    @Value("${ipadds}")
    public String IPkey;

    /**
     * 删除缓存
     *
     * @param key
     */
    public  void del(String key) {
        try {
            key = IPkey + ":" + key;
            redisTemplate.delete(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @return true成功 false 失败
     */
    public  boolean set(String key, Object value) {
        String keys = IPkey + ":" + key;
        try {
            redisTemplate.opsForValue().set(keys, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time 值
     * @return true成功 false 失败
     */
    public  boolean setTime(String key,String value,int time){
        try {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.MINUTES);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通缓存放入IP
     *
     * @param key   键
     * @param value 值
     * @return true成功 false 失败
     */
    public  boolean setIPkey(String key, Object value) {
        String keys = "ips:" + key;
        try {
            redisTemplate.opsForValue().set(keys, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 获取 keys 的集合
     * 获取所有集合 传入 *
     */
    public  List<Object> getKeys(String key) {
        try {
            Set<String> keys = redisTemplate.keys(key);
            List<Object> list = redisTemplate.opsForValue().multiGet(keys);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList();
        }
    }

    /**
     * 获取 所有 IP 的集合
     * 获取所有集合 传入 *
     */
    public  List<Object> getIps() {
        try {
            Set<String> keys = redisTemplate.keys("ips:*");
            List<Object> list = redisTemplate.opsForValue().multiGet(keys);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList();
        }
    }

    /**
     * 获取 key还有value的集合
     * 获取所有集合 传入 *
     */
    public  Map<String, Object> getKeyAndValue(String key) {
        try {
            Map<String, Object> objectMap = new HashMap<>();
            Set<String> keys = redisTemplate.keys(key);
            for (String k : keys) {
                objectMap.put(k, redisTemplate.opsForValue().get(k));
            }
            return objectMap;
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap();
        }
    }

    /**
     * 获取 key
     */
    public  Object getKey(String key) {
        Object keys;
        try {
            keys = redisTemplate.opsForValue().get(IPkey + ":" +key);
            if(ObjectUtil.isNull(keys)){
                return null;
            }
            return keys;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 重名名key，如果newKey已经存在，则newKey的原值被覆盖
     *
     * @param oldKey
     * @param newKey
     */
    public  void renameKey(String oldKey, String newKey) {
        redisTemplate.rename(oldKey, newKey);
    }

    /**
     * 获取原来key键对应的值并重新赋新值
     *
     * @param key
     * @param value
     */
    public  void getAndSetValue(String key, Object value) {
        try {
            redisTemplate.opsForValue().getAndSet(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  Integer getThreadStatus(Map<String, Object> map, String lotteryKey) {
        for (String lty : map.keySet()) {
            if (lty.indexOf(lotteryKey) != -1) {
                return Integer.parseInt(map.get(lty).toString());
            }
        }
        return 0;
    }


}
