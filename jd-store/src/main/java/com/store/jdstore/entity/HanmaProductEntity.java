package com.store.jdstore.entity;

import lombok.Data;
import jakarta.persistence.*;


@Data
@Entity
@Table(name = "hanma_product")
public class HanmaProductEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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
  private String createTime;
  private String updateTime;

}
