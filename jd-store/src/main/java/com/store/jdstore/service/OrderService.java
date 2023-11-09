package com.store.jdstore.service;

import com.store.jdstore.dto.OrderDto;
import com.store.jdstore.exception.SelfException;

public interface OrderService {

    OrderDto findById(Long id);

    OrderDto orderAdd(OrderDto orderDto) throws SelfException;
}
