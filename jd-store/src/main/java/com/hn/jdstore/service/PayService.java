package com.hn.jdstore.service;


import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.hn.common.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface PayService {

    WxPayMpOrderResult pay(Long id, UserDto userDto, String ip) throws WxPayException;

    String notify(HttpServletRequest request, HttpServletResponse response);
}
