package com.hn.jdstore.domain.vo;

import com.hn.common.dto.Validation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class ProductCategoryVo {

    @Schema(description = "分类id")
    @NotNull(groups = Validation.Update.class)
    private Long id;

    @Schema(description = "所属店铺id")
    private Long shopId;

    @Schema(description = "所属店铺")
    private String shopName;

    @Schema(description = "分类名")
    private String name;

    @Schema(description = "商品类型")
    private String type;

    @Schema(description = "状态 0审核 1正常")
    private Integer status;

    @Schema(description = "创建时间")
    private String createTime;

}
