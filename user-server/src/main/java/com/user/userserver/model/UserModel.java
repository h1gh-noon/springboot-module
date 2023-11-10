package com.user.userserver.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class UserModel {
    private Long id;
    private String username;

    @JsonIgnore
    private String password;
    private String phone;
    private String permissions;
    private Integer status;
    private String createTime;

}
