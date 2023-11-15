package com.hn.user.exceptions;

import com.hn.user.enums.ResponseEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TemplateException extends Exception {

    private Integer code;
    private String msg;
    private Boolean success;
    private Object data;


    public TemplateException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public TemplateException(Integer code, Boolean success, String msg) {
        super(msg);
        this.code = code;
        this.success = success;
        this.msg = msg;
    }


    public TemplateException(Integer code, String msg, Object data) {
        super(msg);
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public TemplateException(ResponseEnum responseEnum) {
        super(responseEnum.getMsg());
        this.code = responseEnum.getCode();
        this.msg = responseEnum.getMsg();
        this.success = responseEnum.getSuccess();
    }

    public TemplateException(ResponseEnum responseEnum, Object data) {
        super(responseEnum.getMsg());
        this.code = responseEnum.getCode();
        this.msg = responseEnum.getMsg();
        this.success = responseEnum.getSuccess();
        this.data = data;
    }
}
