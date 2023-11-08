package com.store.jdstore.model;

import lombok.Data;

import java.util.List;


@Data
public class ShopInfoProductModel {

  private ShopModel shopInfo;

  private List<ProductCategoryModel> productCategory;

  private List<ProductModel> productList;

  public ShopInfoProductModel() {
  }

  public ShopInfoProductModel(ShopModel shopInfo, List<ProductCategoryModel> productCategory,
                              List<ProductModel> productList) {
    this.shopInfo = shopInfo;
    this.productCategory = productCategory;
    this.productList = productList;
  }
}
