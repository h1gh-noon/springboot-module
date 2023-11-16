package com.hn.jdstore.service;

import com.hn.common.exceptions.TemplateException;
import com.hn.jdstore.dto.OrderDto;
import com.hn.jdstore.exception.SelfException;

public interface OrderService {

    OrderDto findById(Long id);

    OrderDto orderAdd(OrderDto orderDto) throws SelfException;
}
