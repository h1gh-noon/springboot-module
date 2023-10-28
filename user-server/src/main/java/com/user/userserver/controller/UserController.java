package com.user.userserver.controller;

import com.user.userserver.entity.User;
import com.user.userserver.model.CommonResponse;
import com.user.userserver.service.UserService;
import com.user.userserver.util.ResponseTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/getUserById")
    public CommonResponse getUserById(@RequestBody User user) {

        if (user.getId() == null) {
            return ResponseTool.getErrorResponse();
        }
        return ResponseTool.getSuccessResponse(userService.getUserById(user.getId()));
    }

    @PostMapping("/getUserPageList")
    public CommonResponse getUserPageList(@RequestBody User user) {

        return ResponseTool.getSuccessResponse(userService.getUserPageList(user));
    }

    @PostMapping("/userAdd")
    public CommonResponse userAdd(@RequestBody User user) {

        int n = userService.userAdd(user);
        if (n > 0) {
            return ResponseTool.getSuccessResponse(user.getId());
        } else {
            return ResponseTool.getErrorResponse();
        }
    }

    @PostMapping("/userUpdate")
    public CommonResponse userUpdate(@RequestBody User user) {

        int n = userService.userUpdate(user);
        if (n > 0) {
            return ResponseTool.getSuccessResponse(user);
        } else {
            return ResponseTool.getErrorResponse();
        }
    }

    @PostMapping("/userUpdateStatus")
    public CommonResponse userUpdateStatus(@RequestBody User user) {

        int n = userService.userUpdateStatus(user);
        if (n > 0) {
            return ResponseTool.getSuccessResponse();
        } else {
            return ResponseTool.getErrorResponse();
        }
    }

    @PostMapping("/userDelete")
    public CommonResponse userDelete(@RequestBody User user) {

        int n = userService.userDelete(user);
        if (n > 0) {
            return ResponseTool.getSuccessResponse();
        } else {
            return ResponseTool.getErrorResponse();
        }
    }
}
