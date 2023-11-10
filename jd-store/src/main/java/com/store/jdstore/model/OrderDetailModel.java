package com.store.jdstore.model;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class OrderDetailModel {

    private Long productId;
    private String name;
    private BigDecimal price;
    private Long quantity;
    private String imgUrl;
}
