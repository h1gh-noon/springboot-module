package com.hn.jdstore.service;

import com.hn.common.dto.UserDto;
import com.hn.jdstore.domain.dto.OrderDto;
import com.hn.jdstore.domain.entity.HanmaOrderDo;
import com.hn.jdstore.exception.SelfException;
import org.springframework.data.domain.Page;

public interface OrderService {

    HanmaOrderDo findById(Long id);

    HanmaOrderDo findByOrderNo(String orderNo);

    OrderDto orderAdd(OrderDto orderDto, String userInfo) throws SelfException;

    void orderPay(HanmaOrderDo orderDo);

    Page<HanmaOrderDo> orderPageList(Integer currentPage, Integer pageSize,
                                     Long shopId, UserDto userDto);

    OrderDto getOrderDetail(Long id);

    void orderCancel(Long id, String userInfo);
}
