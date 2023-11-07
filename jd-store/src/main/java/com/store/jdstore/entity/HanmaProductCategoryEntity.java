package com.store.jdstore.entity;

import lombok.Data;
import jakarta.persistence.*;


@Data
@Entity
@Table(name = "hanma_product_category")
public class HanmaProductCategoryEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long shopId;
  private String name;
  private String type;
  private Long status;

  @Column(insertable = false, updatable = false)
  private String createTime;
  @Column(insertable = false, updatable = false)
  private String updateTime;

}
