package com.hn.jdstore.exception;

import com.hn.jdstore.enums.ExceptionMsgEnum;

public class SelfException extends RuntimeException {
    public SelfException() {
        super();
    }

    public SelfException(String message) {
        super(message);
    }

    public SelfException(ExceptionMsgEnum e) {
        super(e.getMsg());
    }
}
