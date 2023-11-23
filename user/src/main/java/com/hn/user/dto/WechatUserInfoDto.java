package com.hn.user.dto;


import lombok.Data;

@Data
public class WechatUserInfoDto {

    private String openid;
    private String nickname;
    private String headimgurl;

    private Integer errcode;
}
