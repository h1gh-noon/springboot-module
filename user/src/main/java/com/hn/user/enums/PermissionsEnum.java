package com.hn.user.enums;

import lombok.Getter;

@Getter
public enum PermissionsEnum {
    SUPER_ADMIN("super_admin", "超级管理员"),
    ADMIN("admin", "管理员");

    private final String name;
    private final String des;

    PermissionsEnum(String name, String des) {
        this.name = name;
        this.des = des;
    }
}
