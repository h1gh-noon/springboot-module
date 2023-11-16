package com.hn.jdstore.service.impl;

import com.hn.common.exceptions.TemplateException;
import com.hn.jdstore.entity.HanmaOrderDetailEntity;
import com.hn.jdstore.entity.HanmaOrderEntity;
import com.hn.jdstore.entity.HanmaProductEntity;
import com.hn.jdstore.entity.HanmaShopEntity;
import com.hn.jdstore.service.OrderService;
import com.hn.jdstore.service.ProductService;
import com.hn.jdstore.service.ShopService;
import com.hn.jdstore.dao.OrderDao;
import com.hn.jdstore.dao.OrderDetailDao;
import com.hn.jdstore.dto.OrderDto;
import com.hn.jdstore.enums.ExceptionMsgEnum;
import com.hn.jdstore.util.Utils;
import com.hn.jdstore.exception.SelfException;
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
        if (orderDetailEntityList == null || orderDetailEntityList.isEmpty()) {
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
