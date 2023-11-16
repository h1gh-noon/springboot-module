package com.hn.user.controller;

import com.alibaba.fastjson2.JSON;
import com.hn.common.constant.RequestHeaderConstant;
import com.hn.common.enums.ResponseEnum;
import com.hn.common.exceptions.TemplateException;
import com.hn.common.model.CommonResponse;
import com.hn.user.entity.UserEntity;
import com.hn.common.dto.UserDto;
import com.hn.user.model.UserModel;
import com.hn.user.service.UserService;
import com.hn.common.util.ResponseTool;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Resource
    private UserService userService;

    /**
     * 登录
     *
     * @param userDto { username: String, password: String 接收md5大写32位密文** }
     * @return CommonResponse
     */
    @PostMapping("/userLogin")
    public CommonResponse userLogin(@RequestBody @Validated(UserDto.Login.class) UserDto userDto) {

        String resToken = userService.userLogin(userDto);
        if (resToken == null) {
            return ResponseTool.getErrorResponse(200);
        } else {
            // success
            return ResponseTool.getSuccessResponse(resToken);
        }
    }

    /**
     * 通过token获取用户信息
     *
     * @return CommonResponse
     * @RequestHeader token
     */
    @RequestMapping("/userInfo")
    public CommonResponse userInfo(@RequestHeader(name = RequestHeaderConstant.HEADER_TOKEN_INFO) String tokenInfo) {
        // success
        if (tokenInfo.isEmpty()) {
            throw new TemplateException(ResponseEnum.FAIL_401);
        }
        return ResponseTool.getSuccessResponse(JSON.parseObject(tokenInfo, UserModel.class));
    }

    /**
     * 退出登录 (销毁携带的token)
     *
     * @return CommonResponse
     * @RequestHeader token
     */
    @RequestMapping("/loginOut")
    public CommonResponse loginOut(@RequestHeader(name = "token") String token) {
        if (userService.loginOut(token, new UserDto())) {
            // success
            return ResponseTool.getSuccessResponse();
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
        if (userService.loginOutAll(token, new UserDto())) {
            // success
            return ResponseTool.getSuccessResponse();
        }
        return ResponseTool.getErrorResponse(200);
    }
}
