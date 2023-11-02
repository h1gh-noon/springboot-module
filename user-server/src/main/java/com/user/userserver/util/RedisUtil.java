package com.user.userserver.util;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    public boolean expire(String key, long time) {
        return Boolean.TRUE.equals(stringRedisTemplate.expire(key, time, TimeUnit.SECONDS));
    }

    public boolean del(String key) {
        return Boolean.TRUE.equals(stringRedisTemplate.delete(key));
    }


    // string
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    public Object get(String key) {
        return key == null ? null : stringRedisTemplate.opsForValue().get(key);
    }

    // hash
    public void hMSet(String key, Map<String, String> value) {
        stringRedisTemplate.opsForHash().putAll(key, value);
    }

    // list
    public void rPush(String key, List<String> value) {
        stringRedisTemplate.opsForList().rightPushAll(key, value);
    }

    public Long lLen(String key) {
        return stringRedisTemplate.opsForList().size(key);
    }

    public void lRem(String key, long count, Object obj) {
        stringRedisTemplate.opsForList().remove(key, count, obj);
    }

    public String lPop(String key) {
        return stringRedisTemplate.opsForList().leftPop(key);
    }
}
