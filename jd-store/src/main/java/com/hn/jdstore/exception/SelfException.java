package com.hn.jdstore.exception;

import com.hn.jdstore.enums.ExceptionMsgEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class SelfException extends RuntimeException {

    private Integer code;
    private String msg;
    private Boolean success;
    private Object data;

    public SelfException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public SelfException(Integer code, Boolean success, String msg) {
        super(msg);
        this.code = code;
        this.success = success;
        this.msg = msg;
    }


    public SelfException(Integer code, String msg, Object data) {
        super(msg);
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public SelfException(ExceptionMsgEnum responseEnum) {
        super(responseEnum.getMsg());
        this.code = responseEnum.getCode();
        this.msg = responseEnum.getMsg();
        this.success = responseEnum.getSuccess();
    }

    public SelfException(ExceptionMsgEnum responseEnum, Object data) {
        super(responseEnum.getMsg());
        this.code = responseEnum.getCode();
        this.msg = responseEnum.getMsg();
        this.success = responseEnum.getSuccess();
        this.data = data;
    }

}
