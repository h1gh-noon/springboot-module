package com.hn.jdstore.service.impl;

import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.hn.common.dto.UserDto;
import com.hn.common.enums.ResponseEnum;
import com.hn.common.exceptions.TemplateException;
import com.hn.jdstore.dto.OrderDto;
import com.hn.jdstore.service.OrderService;
import com.hn.jdstore.service.PayService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class PayServiceImpl implements PayService {

    @Resource
    private WxPayService wxPayService;

    @Resource
    private OrderService orderService;


    @Override
    public String pay(Long id, UserDto userDto, String ip) throws WxPayException {


        OrderDto order = orderService.findById(id);
        if (order == null || order.getOrderStatus() != 0 || order.getPayStatus() != 0) {
            throw new TemplateException(ResponseEnum.FAIL_404);
        }

        WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
        orderRequest.setBody("主题");
        orderRequest.setOutTradeNo(order.getOrderNo());
        orderRequest.setTotalFee(BaseWxPayRequest.yuanToFen(order.getOrderAmount().toString()));//元转成分
        orderRequest.setOpenid(userDto.getOpenid());
        orderRequest.setSpbillCreateIp(ip);

        return wxPayService.createOrder(orderRequest);

    }
}
