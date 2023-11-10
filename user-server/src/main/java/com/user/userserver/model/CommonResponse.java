package com.user.userserver.model;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommonResponse {

    private Integer code;
    private Boolean success;
    private Object data;
    private String msg;

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

}
