package com.store.jdstore.entity;

import lombok.Data;
import jakarta.persistence.*;

import java.math.BigDecimal;


@Data
@Entity
@Table(name = "hanma_order_detail")
public class HanmaOrderDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private Long productId;
    private String name;
    private BigDecimal price;
    private Long quantity;
    private String imgUrl;
    private String createTime;
    private String updateTime;

}
