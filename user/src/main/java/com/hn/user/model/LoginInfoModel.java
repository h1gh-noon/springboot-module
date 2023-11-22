package com.hn.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginInfoModel {

    @Schema(description = "token")
   private String token;

    @Schema(description = "user信息")
    private UserModel userInfo;
}
