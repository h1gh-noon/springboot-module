package com.user.userserver.util;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    public Object get(String key) {
        return key == null ? null : stringRedisTemplate.opsForValue().get(key);
    }

    public boolean expire(String key, long time) {
        return Boolean.TRUE.equals(stringRedisTemplate.expire(key, time, TimeUnit.SECONDS));
    }

    public void set(String key, HashMap<String, String> value) {
        stringRedisTemplate.opsForHash().putAll(key, value);
    }
}
