package com.hn.jdstore.service;

import com.hn.common.dto.UserDto;
import com.hn.jdstore.dto.OrderDto;
import com.hn.jdstore.entity.HanmaOrderEntity;
import com.hn.jdstore.exception.SelfException;
import org.springframework.data.domain.Page;

public interface OrderService {

    HanmaOrderEntity findById(Long id);

    HanmaOrderEntity findByOrderNo(String orderNo);

    OrderDto orderAdd(OrderDto orderDto, String userInfo) throws SelfException;

    void orderPay(HanmaOrderEntity orderEntity);

    Page<HanmaOrderEntity> orderPageList(Integer currentPage, Integer pageSize,
                                 OrderDto orderDto, UserDto userDto);
}
