package com.user.userserver;

import com.user.userserver.util.RedisUtil;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.*;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class UserServerApplicationTests {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedisUtil redisUtil;

    @Test
    void contextLoads() {

        String userKey = "TOKEN_LIST_USER_ID_1";

        List<String> removeKeys = new ArrayList<>();
        removeKeys.add(userKey);
        removeKeys.addAll(redisUtil.lRange(userKey));
        System.out.println(redisUtil.del(removeKeys));
    }

    @Test
    void contextLoads1() {

        List<String> stringList = new ArrayList<>();
        stringList.add("s");

        SessionCallback<List<Object>> callback = new SessionCallback<>() {
            @Override
            public List<Object> execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                operations.opsForValue().set("token", "aa");
                operations.opsForList().rightPushAll("userKey", stringList);
                return operations.exec();
            }
        };


        System.out.println(stringRedisTemplate.execute(callback));
    }

}
