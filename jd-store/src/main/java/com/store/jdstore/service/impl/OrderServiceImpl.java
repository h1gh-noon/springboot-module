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
import com.store.jdstore.service.ProductCategoryService;
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
    private ProductCategoryService productCategoryService;

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

        HanmaOrderEntity hanmaOrderEntity = new HanmaOrderEntity();
        BeanUtils.copyProperties(orderDto, hanmaOrderEntity);
        hanmaOrderEntity.setId(null);
        hanmaOrderEntity.setOrderNo(Utils.getTimeRandom());
        hanmaOrderEntity.setOrderAmount(new BigDecimal(0));
        hanmaOrderEntity.setPayTime(null);
        hanmaOrderEntity.setPayStatus(null);
        hanmaOrderEntity.setOrderStatus(1);
        hanmaOrderEntity.setCreateTime(Utils.getTimestampStr());
        hanmaOrderEntity.setUpdateTime(hanmaOrderEntity.getCreateTime());

        List<HanmaOrderDetailEntity> orderDetailEntityList = orderDto.getDetailList();

        if (orderDetailEntityList == null || orderDetailEntityList.isEmpty()){
            return null;
        }

        List<HanmaProductEntity> detailList = orderDetailEntityList.stream().map(e -> {
            HanmaProductEntity p = productService.findById(e.getProductId());
            if (p == null || p.getProductStock() < e.getQuantity()) {
                throw new SelfException(ExceptionMsgEnum.NO_STACK);
            }
            p.setProductStock(p.getProductStock() - e.getQuantity());
            hanmaOrderEntity.setOrderAmount(hanmaOrderEntity.getOrderAmount().add(p.getPrice().multiply(new BigDecimal(p.getProductStock()))));
            e.setId(null);
            e.setName(p.getName());
            e.setPrice(p.getPrice());
            e.setImgUrl(p.getImgUrl());
            e.setCreateTime(hanmaOrderEntity.getCreateTime());
            e.setUpdateTime(hanmaOrderEntity.getCreateTime());
            // productService.update(p);
            // HanmaOrderDetailEntity hanmaOrderDetailEntity = new HanmaOrderDetailEntity();
            // BeanUtils.copyProperties(p, hanmaOrderDetailEntity);
            return p;
        }).collect(Collectors.toList());

        HanmaShopEntity shopEntity = shopService.findById(hanmaOrderEntity.getShopId());
        hanmaOrderEntity.setShopName(shopEntity.getName());
        orderDto.setShopName(shopEntity.getName());

        orderDao.save(hanmaOrderEntity);
        System.out.println(hanmaOrderEntity.getId());
        orderDetailEntityList.forEach(e -> e.setOrderId(hanmaOrderEntity.getId()));

        // 减少库存
        productService.update(detailList);

        // 创建订单详情
        orderDto.setDetailList(orderDetailDao.saveAll(orderDetailEntityList));

        BeanUtils.copyProperties(hanmaOrderEntity, orderDto);

        return orderDto;
    }
}
