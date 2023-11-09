package com.store.jdstore.service.impl;

import com.store.jdstore.dto.OrderDto;
import com.store.jdstore.entity.HanmaProductEntity;
import com.store.jdstore.enums.ExceptionMsgEnum;
import com.store.jdstore.exception.SelfException;
import com.store.jdstore.service.OrderService;
import com.store.jdstore.service.ProductCategoryService;
import com.store.jdstore.service.ProductService;
import jakarta.annotation.Resource;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
public class OrderServiceImpl implements OrderService {

    @Resource
    private ProductCategoryService productCategoryService;

    @Resource
    private ProductService productService;

    @Override
    public OrderDto findById(Long id) {
        return null;
    }

    @Override
    @Transactional
    public OrderDto orderAdd(OrderDto orderDto) throws SelfException{

        List<HanmaProductEntity> detailList = orderDto.getDetailList().stream().map(e -> {
            HanmaProductEntity p = productService.findById(e.getId());
            if (p == null) {
                throw new SelfException(ExceptionMsgEnum.NO_STACK);
            }
            return p;
        }).collect(Collectors.toList());



        return null;
    }
}
