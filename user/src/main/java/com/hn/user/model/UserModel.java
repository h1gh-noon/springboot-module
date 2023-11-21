package com.hn.user.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class UserModel {
    @Schema(description = "用户id")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "权限")
    private String permissions;

    @Schema(description = "用户状态")
    private Integer status;

    @Schema(description = "用户创建时间")
    private String createTime;

}
