package com.hn.jdstore.domain.entity;

import lombok.Data;
import jakarta.persistence.*;


@Data
@Entity
@Table(name = "jd_address")
public class AddressDo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long userId;
  private String realName;
  private String mobile;
  private String city;
  private String area;
  private String house;
  private Integer isDefault;
  private String createTime;
  private String updateTime;

}
