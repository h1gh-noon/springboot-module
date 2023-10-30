package com.user.userserver;

import com.user.userserver.service.UserService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServerApplicationTests {

    @Resource
    private UserService userService;

    @Test
    void contextLoads() {
        System.out.println(userService.getUserInfoByToken("47eb86387dcc47c7b190514da0e23b78"));
    }

}
