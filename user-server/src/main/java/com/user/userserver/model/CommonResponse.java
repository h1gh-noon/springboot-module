package com.user.userserver.model;


public class CommonResponse {

    private Integer code;
    private Boolean success;
    private Object data;
    private String msg;

    public CommonResponse() {
    }

    public CommonResponse(Integer code) {
        this.code = code;
    }

    public CommonResponse(Integer code, Boolean success) {
        this.code = code;
        this.success = success;
    }

    public CommonResponse(Integer code, Boolean success, Object data) {
        this.code = code;
        this.success = success;
        this.data = data;
    }

    public CommonResponse(Integer code, Boolean success, Object data, String msg) {
        this.code = code;
        this.success = success;
        this.data = data;
        this.msg = msg;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
