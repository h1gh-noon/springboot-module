package com.hn.jdstore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class SearchModel {
    @Schema(description = "商品列表")
    private List<ProductModel> products;
    @Schema(description = "店铺列表")
    private List<ShopModel> shops;
}
