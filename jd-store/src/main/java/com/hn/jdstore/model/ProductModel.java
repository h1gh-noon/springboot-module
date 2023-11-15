package com.hn.jdstore.model;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class ProductModel {

  private Long id;
  private String name;
  private Long sales;
  private BigDecimal price;
  private BigDecimal oldPrice;
  private Long productStock;
  private String imgUrl;
  private String type;
  private Long shopId;
  private Integer status;

}
