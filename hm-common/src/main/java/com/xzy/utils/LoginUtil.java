package com.xzy.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class LoginUtil {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private String loginKey = "hm2_token:";
    private int expire = 3600;

    /**
     * 从redis查询令牌
     *
     * @param uid
     * @return
     */
    public String selectToken(String uid) {
        String key = loginKey + uid + ":*";
        //匹配获取到的key
        Set<String> keys = stringRedisTemplate.keys(key);
        if (0 == keys.size()) {
            return null;
        }
        //从redis中取到令牌信息
        String value = stringRedisTemplate.opsForValue().get(keys.iterator().next());
        return value;
    }

    /**
     * 查询所有的令牌信息
     *
     * @return
     */
    public List selectTokenList() {
        String key = loginKey + "*";
        Set<String> keys = stringRedisTemplate.keys(key);
        List list = stringRedisTemplate.opsForValue().multiGet(keys);
        return list;
    }

    /**
     * 从redis查询令牌
     *
     * @param userId
     * @return
     */
    public String selectTokenByUserId(Integer userId) {
        String key = loginKey + "*:" + userId;
        //匹配获取到的key
        Set<String> keys = stringRedisTemplate.keys(key);
        if (0 == keys.size()) {
            return null;
        }
        //从redis中取到令牌信息
        String value = stringRedisTemplate.opsForValue().get(keys.iterator().next());
        return value;
    }

    public void refreshToken(String key) {
        //从redis中取到令牌信息
        stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    /**
     * 存储到令牌到redis
     *
     * @return
     */
    public void saveToken(String uid, String id, String content) {
        this.delTokenById(new String[]{id});
        String key = loginKey + uid + ":" + id;
        stringRedisTemplate.boundValueOps(key).set(content, expire, TimeUnit.SECONDS);
    }

    /**
     * 根据uid删除token
     *
     * @param uid
     * @return
     */
    public boolean delToken(String uid) {
        String key = loginKey + uid + "*";
        stringRedisTemplate.delete(stringRedisTemplate.keys(key));
        return true;
    }

    /**
     * 根据id删除token
     *
     * @param ids
     * @return
     */
    public boolean delTokenById(String[] ids) {
        if (null == ids) {
            return true;
        }
        List<String> keys = new ArrayList<>();
        for (String id : ids) {
            stringRedisTemplate.keys(loginKey + "*:" + id).forEach(key -> keys.add(key));
        }
        if (keys.size() > 0) {
            stringRedisTemplate.delete(keys);
        }
        return true;
    }

    public boolean delAll() {
        Set keys = stringRedisTemplate.keys(loginKey + "*");
        if (keys.size() > 0) {
            stringRedisTemplate.delete(keys);
        }
        return true;
    }

}
