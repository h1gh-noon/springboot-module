package com.store.jdstore.controller;

import com.store.jdstore.dto.OrderDto;
import com.store.jdstore.model.CommonResponse;
import com.store.jdstore.model.OrderDetailModel;
import com.store.jdstore.model.OrderModel;
import com.store.jdstore.service.OrderService;
import com.store.jdstore.util.ResponseUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Resource
    private OrderService orderService;

    @PostMapping("/orderAdd")
    public CommonResponse orderAdd(@RequestBody OrderDto orderDto) {
        OrderDto resOrderDto = orderService.orderAdd(orderDto);

        OrderModel orderModel = new OrderModel();
        BeanUtils.copyProperties(resOrderDto, orderModel);

        List<OrderDetailModel> orderDetailModels = resOrderDto.getDetailList().stream().map(e -> {
            OrderDetailModel o = new OrderDetailModel();
            BeanUtils.copyProperties(e, o);
            return o;
        }).collect(Collectors.toList());
        orderModel.setDetailList(orderDetailModels);
        return ResponseUtil.getSuccessResponse(orderModel);
    }

}
