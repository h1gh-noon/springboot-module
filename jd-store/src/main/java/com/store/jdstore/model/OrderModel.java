package com.store.jdstore.model;

import lombok.Data;

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
    private Double orderAmount;
    private Long payTime;
    private Long payStatus;
    private Long orderStatus;
    private String createTime;
    private String updateTime;

    private List<OrderDetailModel> detailList;

}
