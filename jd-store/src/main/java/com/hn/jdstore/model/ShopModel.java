package com.hn.jdstore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
public class ShopModel {

  @Schema(description = "店铺id")
  private Long id;

  @Schema(description = "店铺分类id")
  private Long cateId;

  @Schema(description = "店铺名称")
  private String name;

  @Schema(description = "销量")
  private Long sales;

  @Schema(description = "起送价格")
  private Long expressLimit;

  @Schema(description = "起送运费")
  private Double expressPrice;

  @Schema(description = "优惠描述")
  private String desc;

  @Schema(description = "店铺logo")
  private String imgUrl;

  @Schema(description = "是否热门 0普通 1热铺")
  private Long state;

  @Schema(description = "店铺状态 0审核 1正常")
  private Long status;

}
