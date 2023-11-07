package com.user.userserver.entity;


import lombok.Data;

@Data
public class User {

    private Long id;
    private String username;
    private String password;
    private String phone;
    private String permissions;
    private Integer status;
    private Integer isDel;
    private String createTime;
    private String updateTime;

    public User() {
    }

    public User(String username, String password, String phone, String permissions, Integer status, Integer isDel,
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
