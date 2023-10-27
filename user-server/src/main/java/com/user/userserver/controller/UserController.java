package com.user.userserver.controller;

import com.user.userserver.entity.User;
import com.user.userserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/getUserById")
    public Object getUserById(@RequestBody() User user) {

        if (user.getId() == null) {
            return "";
        }
        return userService.getUserById(user.getId());
    }
}
