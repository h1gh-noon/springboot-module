package com.hn.jdstore.controller;

import com.hn.common.exceptions.TemplateException;
import com.hn.common.api.CommonResponse;
import com.hn.common.util.ResponseTool;
import com.hn.jdstore.model.OrderDetailModel;
import com.hn.jdstore.model.OrderModel;
import com.hn.jdstore.service.OrderService;
import com.hn.jdstore.dto.OrderDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "订单接口")
public class OrderController {

    @Resource
    private OrderService orderService;

    @PostMapping("/orderAdd")
    @Operation(summary = "创建订单")
    public CommonResponse<OrderModel> orderAdd(@RequestBody OrderDto orderDto) throws TemplateException {
        OrderDto resOrderDto = orderService.orderAdd(orderDto);

        OrderModel orderModel = new OrderModel();
        BeanUtils.copyProperties(resOrderDto, orderModel);

        List<OrderDetailModel> orderDetailModels = resOrderDto.getDetailList().stream().map(e -> {
            OrderDetailModel o = new OrderDetailModel();
            BeanUtils.copyProperties(e, o);
            return o;
        }).collect(Collectors.toList());
        orderModel.setDetailList(orderDetailModels);
        return ResponseTool.getSuccessResponse(orderModel);
    }

}
