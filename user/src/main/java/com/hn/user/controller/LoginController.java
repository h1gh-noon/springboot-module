package com.hn.user.controller;

import com.alibaba.fastjson2.JSON;
import com.hn.common.api.CommonResponse;
import com.hn.common.constant.RequestHeaderConstant;
import com.hn.common.dto.UserDto;
import com.hn.common.util.ResponseTool;
import com.hn.user.dto.LoginDto;
import com.hn.user.model.UserModel;
import com.hn.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Login")
public class LoginController {

    @Resource
    private UserService userService;

    /**
     * 登录
     *
     * @param loginDto { username: String, password: String 接收md5大写32位密文** }
     * @return CommonResponse
     */
    @Operation(summary = "登录接口", method = "post", description = "登录 data中返回token")
    @PostMapping("/userLogin")
    public CommonResponse<String> userLogin(@RequestBody LoginDto loginDto) {

        String resToken = userService.userLogin(loginDto);
        if (resToken == null) {
            return ResponseTool.getErrorResponse();
        } else {
            // success
            return ResponseTool.getSuccessResponse(resToken);
        }
    }

    /**
     * 通过token获取用户信息
     *
     * RequestHeader token
     * @return CommonResponse
     */
    @Operation(summary = "根据token获取用户信息", method = "post", description = "根据token获取用户信息")
    @RequestMapping("/userInfo")
    public CommonResponse<UserModel> userInfo(@RequestHeader(name = RequestHeaderConstant.HEADER_TOKEN_INFO) String tokenInfo) {
        // success
        return ResponseTool.getSuccessResponse(JSON.parseObject(tokenInfo, UserModel.class));
    }

    /**
     * 退出登录 (销毁携带的token)
     *
     * RequestHeader token
     * @return CommonResponse
     */
    @RequestMapping("/loginOut")
    @Operation(summary = "退出登录", method = "post", description = "退出登录 销毁token")
    public CommonResponse<Boolean> loginOut(@RequestHeader(name = RequestHeaderConstant.HEADER_TOKEN) String token,
                                   @RequestHeader(name =
                                           RequestHeaderConstant.HEADER_TOKEN_INFO) String tokenInfo) {
        UserDto userDto = JSON.parseObject(tokenInfo, UserDto.class);

        if (userService.loginOut(token, userDto)) {
            // success
            return ResponseTool.getSuccessResponse();
        }
        return ResponseTool.getErrorResponse();
    }

    /**
     * 退出所有设备(清除所有token)
     *
     * RequestHeader token
     * @return CommonResponse
     */
    @RequestMapping("/loginOutAll")
    @Operation(summary = "退出所有登录的设备", method = "post", description = "退出所有登录 销毁token")
    public CommonResponse<Boolean> loginOutAll(@RequestHeader(name = RequestHeaderConstant.HEADER_TOKEN_INFO) String tokenInfo) {
        UserDto userDto = JSON.parseObject(tokenInfo, UserDto.class);
        if (userService.loginOutAll(userDto)) {
            // success
            return ResponseTool.getSuccessResponse();
        }
        return ResponseTool.getErrorResponse();
    }
}
