package com.user.userserver.model;


import lombok.Data;

@Data
public class UserModel {
    private Long id;
    private String username;
    private String phone;
    private String permissions;
    private Integer status;
    private String createTime;

}
