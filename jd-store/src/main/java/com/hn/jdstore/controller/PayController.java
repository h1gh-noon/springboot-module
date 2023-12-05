package com.hn.jdstore.controller;

import com.github.binarywang.wxpay.exception.WxPayException;
import com.hn.common.api.CommonResponse;
import com.hn.common.constant.RequestHeaderConstant;
import com.hn.common.dto.UserDto;
import com.hn.common.util.IPUtil;
import com.hn.common.util.ResponseTool;
import com.hn.jdstore.service.PayService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
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
    public CommonResponse<String> pay(Long id,
                                      @RequestHeader(RequestHeaderConstant.HEADER_TOKEN_INFO) UserDto userDto,
                                      HttpServletRequest httpServletRequest) throws WxPayException {
        return ResponseTool.getSuccessResponse(payService.pay(id, userDto, IPUtil.getRequestIp(httpServletRequest)));
    }
}
