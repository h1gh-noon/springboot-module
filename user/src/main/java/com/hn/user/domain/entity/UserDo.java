package com.hn.user.domain.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDo {

    private Long id;
    private String username;
    private String openid;
    private String nickname;
    private String password;
    private String headimgurl;
    private String phone;
    private String permissions;
    private Integer status;
    private Integer isDel;
    private String createTime;
    private String updateTime;

    public UserDo(String username, String password, String phone, String permissions, Integer status, Integer isDel,
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
