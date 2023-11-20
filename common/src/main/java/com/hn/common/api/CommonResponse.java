package com.hn.common.api;


import com.hn.common.enums.ResponseEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommonResponse {

    private Integer code;
    private Boolean success;
    private String msg;
    private Object data;

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

    public CommonResponse(ResponseEnum responseEnum) {
        this.code = responseEnum.getCode();
        this.success = responseEnum.getSuccess();
        this.msg = responseEnum.getMsg();
    }

    public CommonResponse(ResponseEnum responseEnum, Object data) {
        this.code = responseEnum.getCode();
        this.success = responseEnum.getSuccess();
        this.msg = responseEnum.getMsg();
        this.data = data;
    }

}
