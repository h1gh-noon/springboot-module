package com.user.userserver;

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

    @Test
    void contextLoads() {
        String s = stringRedisTemplate.opsForList().leftPop("TOKEN_LIST_USER_ID_10");
        System.out.println(s);
    }

    @Test
    void contextLoads1() {

        List<String> stringList = new ArrayList<>();
        stringList.add("s");

        // SessionCallback<List<Object>> callback = (operations) -> {
        //     operations.multi();
        //     operations.opsForValue().set("token", "aa");
        //     operations.opsForList().rightPushAll("userKey", stringList);
        //     return operations.exec();
        // };

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
