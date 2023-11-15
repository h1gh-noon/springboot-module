package com.hn.jdstore.entity;

import lombok.Data;
import jakarta.persistence.*;

import java.math.BigDecimal;


@Data
@Entity
@Table(name = "hanma_product")
public class HanmaProductEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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
  private String createTime;
  private String updateTime;

}
