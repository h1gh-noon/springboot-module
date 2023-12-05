package com.hn.jdstore.service;

import com.hn.jdstore.dto.OrderDto;
import com.hn.jdstore.exception.SelfException;

public interface OrderService {

    OrderDto findById(Long id);

    OrderDto orderAdd(OrderDto orderDto, String userInfo) throws SelfException;
}
