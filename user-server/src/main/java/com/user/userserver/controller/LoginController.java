package com.user.userserver.controller;

import com.user.userserver.model.CommonResponse;
import com.user.userserver.model.UserInfo;
import com.user.userserver.service.UserService;
import com.user.userserver.util.ResponseTool;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Resource
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

    /**
     * 通过token获取用户信息
     *
     * @return CommonResponse
     * @RequestHeader token
     */
    @RequestMapping("/userInfo")
    public CommonResponse userInfo(@RequestHeader(name = "token") String token) {
        UserInfo userInfo = userService.getUserInfoByToken(token);
        if (userInfo != null) {
            // success
            return ResponseTool.getSuccessResponse(userInfo);
        }
        return ResponseTool.getErrorResponse(200);
    }

    /**
     * 退出登录 (销毁携带的token)
     *
     * @return CommonResponse
     * @RequestHeader token
     */
    @RequestMapping("/loginOut")
    public CommonResponse loginOut(@RequestHeader(name = "token") String token) {
        UserInfo userInfo = userService.getUserInfoByToken(token);
        if (userInfo != null) {
            // success
            return ResponseTool.getSuccessResponse(userInfo);
        }
        return ResponseTool.getErrorResponse(200);
    }

    /**
     * 退出所有设备(清除所有token)
     *
     * @return CommonResponse
     * @RequestHeader token
     */
    @RequestMapping("/loginOutAll")
    public CommonResponse loginOutAll(@RequestHeader(name = "token") String token) {
        UserInfo userInfo = userService.getUserInfoByToken(token);
        if (userInfo != null) {
            // success
            return ResponseTool.getSuccessResponse(userInfo);
        }
        return ResponseTool.getErrorResponse(200);
    }
}
