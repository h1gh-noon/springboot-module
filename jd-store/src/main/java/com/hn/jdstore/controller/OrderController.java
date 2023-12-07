package com.hn.jdstore.controller;

import com.alibaba.fastjson2.JSON;
import com.hn.common.api.CommonResponse;
import com.hn.common.api.PaginationData;
import com.hn.common.constant.PermissionConstant;
import com.hn.common.constant.RequestHeaderConstant;
import com.hn.common.dto.UserDto;
import com.hn.common.enums.ResponseEnum;
import com.hn.common.exceptions.TemplateException;
import com.hn.common.util.ResponseTool;
import com.hn.jdstore.dto.OrderDto;
import com.hn.jdstore.entity.HanmaOrderEntity;
import com.hn.jdstore.model.OrderDetailModel;
import com.hn.jdstore.model.OrderModel;
import com.hn.jdstore.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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
    public CommonResponse<OrderModel> orderAdd(@RequestBody OrderDto orderDto,
                                               @RequestHeader(RequestHeaderConstant.HEADER_TOKEN_INFO) String userInfo) throws TemplateException {
        OrderDto resOrderDto = orderService.orderAdd(orderDto, userInfo);

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

    @PostMapping("/orderPageList")
    @Operation(summary = "订单列表")
    public CommonResponse<PaginationData<List<OrderModel>>>
    orderPageList(
            @RequestParam(required = false, defaultValue = "1") @Schema(description = "当前页码") Integer currentPage,
            @RequestParam(required = false, defaultValue = "20") @Schema(description = "每页条数") Integer pageSize,
            @RequestBody OrderDto orderDto,
            @RequestHeader(RequestHeaderConstant.HEADER_TOKEN_INFO) String userInfo)
            throws TemplateException {


        Page<HanmaOrderEntity> orderDtos = orderService.orderPageList(currentPage, pageSize,
                orderDto, JSON.parseObject(userInfo, UserDto.class));
        log.info("orderDtos={}", orderDtos);

        List<OrderModel> list = new ArrayList<>();
        orderDtos.getContent().forEach(h -> {
            OrderModel sm = new OrderModel();
            BeanUtils.copyProperties(h, sm);
            list.add(sm);
        });

        PaginationData<List<OrderModel>> pagination = new PaginationData<>();
        pagination.setCurrentPage(currentPage);
        pagination.setPageSize(pageSize);
        pagination.setTotal(orderDtos.getTotalElements());
        pagination.setData(list);

        return ResponseTool.getSuccessResponse(pagination);

    }

    @PostMapping("/getOrderDetail")
    @Operation(summary = "订单详情")
    public CommonResponse<OrderModel> getOrderDetail(
            @RequestBody OrderDto orderDto,
            @RequestHeader(RequestHeaderConstant.HEADER_TOKEN_INFO) String userInfo)
            throws TemplateException {

        UserDto userDto = JSON.parseObject(userInfo, UserDto.class);
        List<String> permissions = Arrays.stream(userDto.getPermissions().split(",")).toList();

        OrderDto orderDetail = orderService.getOrderDetail(orderDto);
        // 权限验证
        if (!Objects.equals(orderDetail.getUserId(), userDto.getId())
                && !permissions.contains(PermissionConstant.SUPER_ADMIN)
                && !permissions.contains(PermissionConstant.ADMIN)
        ) {
            throw new TemplateException(ResponseEnum.FAIL_404);
        }

        OrderModel orderModel = new OrderModel();
        BeanUtils.copyProperties(orderDetail, orderModel);
        List<OrderDetailModel> orderDetailModels = orderDetail.getDetailList().stream().map(e -> {
            OrderDetailModel o = new OrderDetailModel();
            BeanUtils.copyProperties(e, o);
            return o;
        }).collect(Collectors.toList());

        orderModel.setDetailList(orderDetailModels);

        return ResponseTool.getSuccessResponse(orderModel);

    }

}
