package com.hn.jdstore.service;


import com.github.binarywang.wxpay.exception.WxPayException;
import com.hn.common.dto.UserDto;

public interface PayService {

    String pay(Long id, UserDto userDto, String ip) throws WxPayException;
}
