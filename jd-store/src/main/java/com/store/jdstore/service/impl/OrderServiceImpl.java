package com.store.jdstore.service.impl;

import com.store.jdstore.dao.OrderDao;
import com.store.jdstore.dao.OrderDetailDao;
import com.store.jdstore.dto.OrderDto;
import com.store.jdstore.entity.HanmaOrderDetailEntity;
import com.store.jdstore.entity.HanmaOrderEntity;
import com.store.jdstore.entity.HanmaProductEntity;
import com.store.jdstore.entity.HanmaShopEntity;
import com.store.jdstore.enums.ExceptionMsgEnum;
import com.store.jdstore.exception.SelfException;
import com.store.jdstore.service.OrderService;
import com.store.jdstore.service.ProductService;
import com.store.jdstore.service.ShopService;
import com.store.jdstore.util.Utils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private ProductService productService;

    @Resource
    private ShopService shopService;

    @Resource
    private OrderDao orderDao;

    @Resource
    private OrderDetailDao orderDetailDao;

    @Override
    public OrderDto findById(Long id) {
        return null;
    }

    @Override
    @Transactional
    public OrderDto orderAdd(OrderDto orderDto) throws SelfException {

        List<HanmaOrderDetailEntity> orderDetailEntityList = orderDto.getDetailList();
        if (orderDetailEntityList == null || orderDetailEntityList.isEmpty()){
            return null;
        }

        orderDto.setId(null);
        orderDto.setOrderNo(Utils.getTimeRandom());
        orderDto.setOrderAmount(new BigDecimal(0));
        orderDto.setPayTime(null);
        orderDto.setPayStatus(null);
        orderDto.setOrderStatus(1);
        orderDto.setCreateTime(Utils.getTimestampStr());
        orderDto.setUpdateTime(orderDto.getCreateTime());

        List<HanmaProductEntity> detailList = orderDetailEntityList.stream().map(e -> {
            HanmaProductEntity p = productService.findById(e.getProductId());
            if (p == null || p.getProductStock() < e.getQuantity()) {
                throw new SelfException(ExceptionMsgEnum.NO_STACK);
            }
            p.setProductStock(p.getProductStock() - e.getQuantity());
            orderDto.setOrderAmount(orderDto.getOrderAmount().add(p.getPrice().multiply(new BigDecimal(e.getQuantity()))));
            e.setId(null);
            e.setName(p.getName());
            e.setPrice(p.getPrice());
            e.setImgUrl(p.getImgUrl());
            e.setCreateTime(orderDto.getCreateTime());
            e.setUpdateTime(orderDto.getCreateTime());
            return p;
        }).collect(Collectors.toList());

        HanmaShopEntity shopEntity = shopService.findById(orderDto.getShopId());
        orderDto.setShopName(shopEntity.getName());

        HanmaOrderEntity hanmaOrderEntity = new HanmaOrderEntity();
        BeanUtils.copyProperties(orderDto, hanmaOrderEntity);


        orderDao.save(hanmaOrderEntity);
        orderDto.setId(hanmaOrderEntity.getId());
        orderDetailEntityList.forEach(e -> e.setOrderId(hanmaOrderEntity.getId()));

        // 减少库存
        productService.update(detailList);

        // 创建订单详情
        orderDto.setDetailList(orderDetailDao.saveAll(orderDetailEntityList));

        return orderDto;
    }
}
