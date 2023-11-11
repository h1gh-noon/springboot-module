package com.user.userserver.enums;

public enum ResponseEnum {

    SUCCESS_200(200, true, "success"),
    SUCCESS_200_NO_MSG(200, true, null),
    FAIL_401(401, false, "404了"),
    FAIL_404(404, false, "404了"),
    Unauthorized(401, false, "需要登录！"),
    Authorization(403, false, "权限不足！"),

    HAS_USERNAME(500, false, "用户名已存在！");
    // FAIL_500(500, false, "500了");

    private final Integer code;
    private final Boolean success;
    private final String msg;

    ResponseEnum(Integer code, Boolean success, String msg) {
        this.code = code;
        this.success = success;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Boolean getSuccess() {
        return success;
    }
}
