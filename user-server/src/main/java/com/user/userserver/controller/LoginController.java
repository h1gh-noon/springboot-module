package com.user.userserver.controller;

import com.user.userserver.model.CommonResponse;
import com.user.userserver.service.UserService;
import com.user.userserver.util.ResponseTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 登录
     *
     * @param data { username: String, password: String 接收md5大写32位密文** }
     * @return CommonResponse
     */
    @PostMapping("/userLogin")
    public CommonResponse userLogin(@RequestBody Map<String, String> data) {
        if (data.containsKey("username") && data.containsKey("password")) {
            String resToken = userService.userLogin(data);
            if (resToken == null) {
                return ResponseTool.getErrorResponse(200);
            } else {
                // success
                return ResponseTool.getSuccessResponse(resToken);
            }
        }

        return ResponseTool.getErrorResponse();
    }
}
