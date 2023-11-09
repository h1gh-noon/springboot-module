package com.store.jdstore.model;

import lombok.Data;


@Data
public class OrderDetailModel {

    private Long productId;
    private String name;
    private Double price;
    private Long quantity;
    private String imgUrl;
}
