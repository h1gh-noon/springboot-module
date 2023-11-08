package com.store.jdstore.model;

import lombok.Data;


@Data
public class ProductModel {

  private Long id;
  private String name;
  private Long sales;
  private Double price;
  private Double oldPrice;
  private Long productStock;
  private String imgUrl;
  private String type;
  private Long shopId;
  private Long status;

}
