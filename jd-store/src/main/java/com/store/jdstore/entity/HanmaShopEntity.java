package com.store.jdstore.entity;

import lombok.Data;
import jakarta.persistence.*;


@Data
@Entity
@Table(name = "hanma_shop")
public class HanmaShopEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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

  @Column(insertable = false, updatable = false)
  private String createTime;

  @Column(insertable = false, updatable = false)
  private String updateTime;

}
