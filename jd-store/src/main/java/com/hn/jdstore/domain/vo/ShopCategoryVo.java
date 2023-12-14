package com.hn.jdstore.domain.vo;

import com.hn.common.dto.Validation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
@Schema
public class ShopCategoryVo {

    @Schema(description = "店铺分类id")
    @NotNull(groups = {Validation.Update.class})
    private Long id;

    @Schema(description = "店铺分类名")
    @NotBlank(groups = {Validation.Save.class})
    private String name;
    @Schema(description = "分类预览图")
    private String imgUrl;
    @Schema(description = "状态 默认1正常 0禁用")
    private Integer status;

}
