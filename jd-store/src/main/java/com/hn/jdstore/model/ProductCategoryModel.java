package com.hn.jdstore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
public class ProductCategoryModel {

    @Schema(description = "分类id")
    private Long id;

    @Schema(description = "所属店铺id")
    private Long shopId;

    @Schema(description = "分类名")
    private String name;

    @Schema(description = "商品类型")
    private String type;

    @Schema(description = "状态 0审核 1正常")
    private Integer status;

}
