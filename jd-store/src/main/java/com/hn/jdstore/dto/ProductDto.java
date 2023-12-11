package com.hn.jdstore.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {

    private Long id;
    private String name;
    private Long sales;
    private BigDecimal price;
    private BigDecimal oldPrice;
    private Long productStock;
    private String imgUrl;
    private Long cateId;

    private Long shopId;
    private String shopName;
    private String shopImg;
    private Long shopState;
    private String shopDescDetail;
    private Long shopExpressLimit;
    private BigDecimal shopExpressPrice;

}
