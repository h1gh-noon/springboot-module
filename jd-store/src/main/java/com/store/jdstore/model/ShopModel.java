package com.store.jdstore.model;

import lombok.Data;


@Data
public class ShopModel {

  private Long id;
  private Long cateId;
  private String name;
  private Long sales;
  private Long expressLimit;
  private Double expressPrice;
  private String desc;
  private String imgUrl;
  private Long state;
  private Long status;

}
