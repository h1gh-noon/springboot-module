package com.hn.jdstore.service.impl;

import com.alibaba.fastjson2.JSON;
import com.hn.common.constant.PermissionConstant;
import com.hn.common.dto.UserDto;
import com.hn.common.enums.ResponseEnum;
import com.hn.common.exceptions.TemplateException;
import com.hn.common.util.Util;
import com.hn.jdstore.dao.OrderDao;
import com.hn.jdstore.dao.OrderDetailDao;
import com.hn.jdstore.dto.OrderDto;
import com.hn.jdstore.entity.HanmaOrderDetailEntity;
import com.hn.jdstore.entity.HanmaOrderEntity;
import com.hn.jdstore.entity.HanmaProductEntity;
import com.hn.jdstore.entity.HanmaShopEntity;
import com.hn.jdstore.enums.ExceptionMsgEnum;
import com.hn.jdstore.exception.SelfException;
import com.hn.jdstore.service.OrderService;
import com.hn.jdstore.service.ProductService;
import com.hn.jdstore.service.ShopService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
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
    public HanmaOrderEntity findById(Long id) {
        return orderDao.findById(id).orElse(null);
    }

    @Override
    public HanmaOrderEntity findByOrderNo(String orderNo) {
        return orderDao.findByOrderNo(orderNo);
    }

    @Override
    @Transactional
    public OrderDto orderAdd(OrderDto orderDto, String userInfo) throws SelfException {

        UserDto userDto = JSON.parseObject(userInfo, UserDto.class);

        List<HanmaOrderDetailEntity> orderDetailEntityList = orderDto.getDetailList();
        if (orderDetailEntityList == null || orderDetailEntityList.isEmpty()) {
            return null;
        }

        orderDto.setId(null);
        orderDto.setOrderNo(Util.getTimeRandom());
        orderDto.setOrderAmount(new BigDecimal(0));
        orderDto.setPayTime(null);
        orderDto.setPayStatus(null);
        orderDto.setOrderStatus(0);
        orderDto.setCreateTime(Util.getTimestampStr());
        orderDto.setUpdateTime(orderDto.getCreateTime());

        orderDto.setUserId(userDto.getId());
        orderDto.setUserOpenid(userDto.getOpenid());

        List<HanmaProductEntity> detailList = orderDetailEntityList.stream().map(e -> {
            HanmaProductEntity p = productService.findById(e.getProductId());
            if (p == null || p.getStatus().equals(0) || p.getProductStock() < e.getQuantity()) {
                // 未查到 || 未上架 || 库存不够
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

        hanmaOrderEntity.setPayStatus(0);
        orderDao.save(hanmaOrderEntity);
        orderDto.setId(hanmaOrderEntity.getId());
        orderDetailEntityList.forEach(e -> e.setOrderId(hanmaOrderEntity.getId()));

        // 减少库存
        productService.update(detailList);

        // 创建订单详情
        orderDto.setDetailList(orderDetailDao.saveAll(orderDetailEntityList));

        return orderDto;
    }

    @Override
    public void orderPay(HanmaOrderEntity orderEntity) {

        orderDao.save(orderEntity);

    }

    @Override
    public Page<HanmaOrderEntity> orderPageList(Integer currentPage, Integer pageSize,
                                                OrderDto orderDto, UserDto userDto) {

        Pageable p = PageRequest.of(currentPage - 1, pageSize);

        List<String> permissions = Arrays.asList(userDto.getPermissions().split(","));
        if (permissions.contains(PermissionConstant.SUPER_ADMIN)) {
            return orderDao.findAll(p);
        } else if (permissions.contains(PermissionConstant.ADMIN)) {
            // 须验证对应的权限再进行查询
            return orderDao.findByShopId(orderDto.getShopId(), p);
        } else {
            return orderDao.findByUserId(userDto.getId(), p);
        }

    }

    @Override
    public OrderDto getOrderDetail(OrderDto orderDto) {
        HanmaOrderEntity hanmaOrderEntity = findById(orderDto.getId());
        if (hanmaOrderEntity == null) {
            throw new TemplateException(ResponseEnum.FAIL_404);
        }

        OrderDto od = new OrderDto();

        BeanUtils.copyProperties(hanmaOrderEntity, od);

        List<HanmaOrderDetailEntity> detailEntityList =
                orderDetailDao.findAllByOrderId(orderDto.getId());

        od.setDetailList(detailEntityList);
        return od;
    }
}
