package com.hn.user.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserListRequest {
    @Schema(description = "用户名")
    private String username;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "用户状态")
    private Integer status;


    @Schema(description = "创建时间 开始范围")
    private String startTime;


    @Schema(description = "创建时间 结束范围")
    private String endTime;

}
