package com.hn.common.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

    @Schema(description = "id 新增时不传")
    @NotNull(groups = Validation.Update.class)
    private Long id;

    @Schema(defaultValue = "admin", description = "用户名")
    @NotBlank(groups = {Validation.Save.class, Validation.Login.class})
    private String username;

    @Schema(defaultValue = "openid", description = "openid")
    private String openid;

    @Schema(defaultValue = "昵称", description = "昵称")
    private String nickname;

    @Schema(description = "微信头像")

    private String headimgurl;

    @Schema(description = "密码")
    @NotBlank(groups = {Validation.Save.class, Validation.Login.class})
    private String password;

    @Pattern(regexp = "^1[0-9]{10}$")
    private String phone;
    private String permissions;
    private Integer status;
    private Integer isDel;
    private String createTime;
    private String updateTime;

    public UserDto(String username, String password, String phone, String permissions, Integer status, Integer isDel,
                   String createTime, String updateTime) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.permissions = permissions;
        this.status = status;
        this.isDel = isDel;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

}
