package com.store.jdstore.entity;

import lombok.Data;
import jakarta.persistence.*;

import java.math.BigDecimal;


@Data
@Entity
@Table(name = "hanma_order")
public class HanmaOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNo;
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

}
