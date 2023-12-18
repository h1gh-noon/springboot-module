package com.hn.jdstore.controller;

import com.alibaba.fastjson2.JSON;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.hn.common.api.CommonResponse;
import com.hn.common.constant.RequestHeaderConstant;
import com.hn.common.dto.UserDto;
import com.hn.common.util.IPUtil;
import com.hn.common.util.ResponseTool;
import com.hn.jdstore.service.PayService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pay")
public class PayController {

    @Resource
    PayService payService;

    @PostMapping("/pay")
    public CommonResponse<Object> pay(Long id,
                                      @Parameter(hidden = true) @RequestHeader(RequestHeaderConstant.HEADER_TOKEN_INFO) String userInfo,
                                      HttpServletRequest httpServletRequest) throws WxPayException {
        return ResponseTool.getSuccessResponse(payService.pay(id, JSON.parseObject(userInfo,
                        UserDto.class),
                IPUtil.getRequestIp(httpServletRequest)));
    }

    @PostMapping("/notify")
    public String notify(
            HttpServletRequest request, HttpServletResponse response) {

        return payService.notify(request, response);
    }
}
