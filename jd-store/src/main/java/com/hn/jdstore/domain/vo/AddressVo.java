package com.hn.jdstore.domain.vo;

import com.hn.common.dto.Validation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class AddressVo {

    @Schema(description = "地址id")
    @NotNull(groups = {Validation.Update.class})
    private Long id;
    @Schema(description = "用户id")
    private Long userId;
    @Schema(description = "收件人姓名")
    private String realName;
    @Schema(description = "电话")
    private String mobile;
    @Schema(description = "城市")
    private String city;
    @Schema(description = "区")
    private String area;
    @Schema(description = "详细地址")
    private String house;
    @Schema(description = "是否为默认地址")
    private Integer isDefault;

}
