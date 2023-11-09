package com.store.jdstore.entity;

import lombok.Data;
import jakarta.persistence.*;


@Data
@Entity
@Table(name = "hanma_order")
public class HanmaOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

}
