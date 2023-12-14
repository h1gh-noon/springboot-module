package com.hn.user.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginInfoVo {

    @Schema(description = "token")
   private String token;

    @Schema(description = "user信息")
    private UserVo userInfo;
}
