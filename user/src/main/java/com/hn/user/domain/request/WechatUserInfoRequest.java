package com.hn.user.domain.request;


import lombok.Data;

@Data
public class WechatUserInfoRequest {

    private String openid;
    private String nickname;
    private String headimgurl;

    private Integer errcode;
}
