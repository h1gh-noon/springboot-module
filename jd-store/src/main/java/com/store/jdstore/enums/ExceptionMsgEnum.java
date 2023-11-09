package com.store.jdstore.enums;

public enum ExceptionMsgEnum {
    NO_STACK("没库存了！");

    private final String msg;

    ExceptionMsgEnum(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

}
