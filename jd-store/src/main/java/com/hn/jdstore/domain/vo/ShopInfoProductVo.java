package com.hn.jdstore.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
public class ShopInfoProductVo {

  @Schema(description = "店铺信息")
  private ShopVo shopInfo;

  @Schema(description = "店铺下的分类列表")
  private List<ProductCategoryVo> productCategory;

  @Schema(description = "店铺的商品列表")
  private List<ProductVo> productList;

  public ShopInfoProductVo(ShopVo shopInfo, List<ProductCategoryVo> productCategory,
                           List<ProductVo> productList) {
    this.shopInfo = shopInfo;
    this.productCategory = productCategory;
    this.productList = productList;
  }
}
