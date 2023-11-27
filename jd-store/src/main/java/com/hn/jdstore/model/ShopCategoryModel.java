package com.hn.jdstore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
@Schema
public class ShopCategoryModel {

    @Schema(description = "店铺分类id")
    private Long id;

    @Schema(description = "店铺分类名")
    private String name;
    @Schema(description = "分类预览图")
    private String imgUrl;
    @Schema(description = "状态 默认1正常 0禁用")
    private Integer status;

}
