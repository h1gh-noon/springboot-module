package com.hn.jdstore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
public class ShopInfoProductModel {

  @Schema(description = "店铺信息")
  private ShopModel shopInfo;

  @Schema(description = "店铺下的分类列表")
  private List<ProductCategoryModel> productCategory;

  @Schema(description = "店铺的商品列表")
  private List<ProductModel> productList;

  public ShopInfoProductModel(ShopModel shopInfo, List<ProductCategoryModel> productCategory,
                              List<ProductModel> productList) {
    this.shopInfo = shopInfo;
    this.productCategory = productCategory;
    this.productList = productList;
  }
}
