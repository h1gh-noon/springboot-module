package com.hn.jdstore.service;

import com.hn.common.dto.UserDto;
import com.hn.jdstore.domain.dto.OrderDto;
import com.hn.jdstore.domain.entity.OrderDo;
import com.hn.jdstore.exception.SelfException;
import org.springframework.data.domain.Page;

public interface OrderService {

    OrderDo findById(Long id);

    OrderDo findByOrderNo(String orderNo);

    OrderDto orderAdd(OrderDto orderDto, String userInfo) throws SelfException;

    void orderPay(OrderDo orderDo);

    Page<OrderDo> orderPageList(Integer currentPage, Integer pageSize,
                                Long shopId, UserDto userDto);

    OrderDto getOrderDetail(Long id);

    void orderCancel(Long id, String userInfo);
}
