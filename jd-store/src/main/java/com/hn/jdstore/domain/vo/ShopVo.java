package com.hn.jdstore.domain.vo;

import com.hn.common.dto.Validation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;


@Data
public class ShopVo {

    @Schema(description = "店铺id")
    @NotNull(groups = {Validation.Update.class})
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
    private BigDecimal expressPrice;

    @Schema(description = "优惠描述")
    private String descDetail;

    @Schema(description = "店铺logo")
    private String imgUrl;

    @Schema(description = "是否热门 0普通 1热铺")
    private Long state;

    @Schema(description = "店铺状态 0审核 1正常")
    private Integer status;

    @Schema(description = "商品列表")
    private List<ProductVo> productList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShopVo shopVo = (ShopVo) o;
        return Objects.equals(id, shopVo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
