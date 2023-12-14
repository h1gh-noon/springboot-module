package com.hn.jdstore.service.impl;

import com.alibaba.cloud.commons.io.IOUtils;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.BaseWxPayResult;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.hn.common.dto.UserDto;
import com.hn.common.enums.ResponseEnum;
import com.hn.common.exceptions.TemplateException;
import com.hn.common.util.Util;
import com.hn.jdstore.domain.entity.OrderDo;
import com.hn.jdstore.service.OrderService;
import com.hn.jdstore.service.PayService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class PayServiceImpl implements PayService {

    @Resource
    private WxPayService wxPayService;

    @Resource
    private OrderService orderService;


    @Override
    public WxPayMpOrderResult pay(Long id, UserDto userDto, String ip) throws WxPayException {


        OrderDo orderDo = orderService.findById(id);
        if (orderDo == null || orderDo.getOrderStatus() != 0 || orderDo.getPayStatus() != 0) {
            throw new TemplateException(ResponseEnum.FAIL_404);
        }

        WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
        orderRequest.setBody("主题");
        orderRequest.setOutTradeNo(orderDo.getOrderNo());
        orderRequest.setTotalFee(BaseWxPayRequest.yuanToFen(orderDo.getOrderAmount().toString()));//元转成分
        orderRequest.setOpenid(userDto.getOpenid());
        orderRequest.setSpbillCreateIp(ip);
        orderRequest.setTradeType(WxPayConstants.TradeType.JSAPI);

        return wxPayService.createOrder(orderRequest);

    }

    @Override
    public String notify(HttpServletRequest request, HttpServletResponse response) {

        try {
            String xmlResult = IOUtils.toString(request.getInputStream(),
                    Charset.forName(request.getCharacterEncoding()));
            WxPayOrderNotifyResult result = wxPayService.parseOrderNotifyResult(xmlResult);

            // 判断订单是否已经支付过，否则可能会重复调用
            String orderNo = result.getOutTradeNo();
            // String tradeNo = result.getTransactionId();
            String totalFee = BaseWxPayResult.fenToYuan(result.getTotalFee());
            log.info("微信回调 订单id={}, 金额={}", orderNo, totalFee);
            OrderDo orderDo = orderService.findByOrderNo(orderNo);
            if (orderDo.getOrderAmount().equals(new BigDecimal(totalFee))
                    && orderDo.getOrderStatus().equals(0)) {

                orderDo.setOrderStatus(1);
                orderDo.setPayStatus(1);

                LocalDateTime pTime = LocalDateTime.parse(result.getTimeEnd(),
                        DateTimeFormatter.ofPattern(
                                "yyyyMMddHHmmss"));
                orderDo.setPayTime(Util.getTimestampStr(pTime));

                orderDo.setUpdateTime(Util.getTimestampStr());
                orderService.orderPay(orderDo);
                log.info("付款处理完成");
                return WxPayNotifyResponse.success("处理成功!");
            }

        } catch (Exception e) {
            log.error("微信回调结果异常,异常原因{}", e.getMessage());
        }
        return WxPayNotifyResponse.fail("异常");

    }
}
