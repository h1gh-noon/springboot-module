package com.hn.jdstore.model;

import lombok.Data;


@Data
public class AddressModel {

  private Long id;
  private Long userId;
  private String realName;
  private String mobile;
  private String city;
  private String area;
  private String house;
  private Integer isDefault;

}
