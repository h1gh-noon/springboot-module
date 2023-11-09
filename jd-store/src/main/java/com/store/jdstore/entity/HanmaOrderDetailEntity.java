package com.store.jdstore.entity;

import lombok.Data;
import jakarta.persistence.*;


@Data
@Entity
@Table(name = "hanma_order_detail")
public class HanmaOrderDetailEntity {

    @Id
    private Long id;
    private Long orderId;
    private Long productId;
    private String name;
    private Double price;
    private Long quantity;
    private String imgUrl;
    private String createTime;
    private String updateTime;

}
