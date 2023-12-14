package com.hn.jdstore.domain.vo;

import com.hn.common.dto.Validation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class ProductVo {

    @Schema(description = "商品id")
    @NotNull(groups = Validation.Update.class)
    private Long id;

    @Schema(description = "商品名")
    private String name;

    @Schema(description = "销量")
    private Long sales;

    @Schema(description = "商品价格")
    private BigDecimal price;

    @Schema(description = "商品原价")
    private BigDecimal oldPrice;

    @Schema(description = "库存")
    private Long productStock;

    @Schema(description = "商品图片")
    private String imgUrl;

    @Schema(description = "商品类型id")
    private Long cateId;

    @Schema(description = "所属店铺id")
    private Long shopId;

    @Schema(description = "状态 0下架 1上架")
    private Integer status;

}
