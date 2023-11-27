package com.hn.jdstore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class OrderDetailModel {

    @Schema(description = "商品id")
    private Long productId;

    @Schema(description = "商品名")
    private String name;

    @Schema(description = "商品价格")
    private BigDecimal price;

    @Schema(description = "商品数量")
    private Long quantity;

    @Schema(description = "商品图片")
    private String imgUrl;
}
