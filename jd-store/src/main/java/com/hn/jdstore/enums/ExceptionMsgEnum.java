package com.hn.jdstore.enums;

import com.hn.common.enums.ResponseEnum;

public enum ExceptionMsgEnum {
    NO_STACK(200, false, "没库存了！");

    private final Integer code;
    private final Boolean success;
    private final String msg;

    ExceptionMsgEnum(Integer code, Boolean success, String msg) {
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
