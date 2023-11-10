package com.store.jdstore.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


@Data
public class OrderModel {

    private Long id;
    private Long orderId;
    private Long userId;
    private String userName;
    private String userPhone;
    private String userAddress;
    private String userOpenid;
    private Long shopId;
    private String shopName;
    private BigDecimal orderAmount;
    private String payTime;
    private Integer payStatus;
    private Integer orderStatus;
    private String createTime;
    private String updateTime;

    private List<OrderDetailModel> detailList;

}
