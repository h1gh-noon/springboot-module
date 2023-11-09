package com.store.jdstore.exception;

import com.store.jdstore.enums.ExceptionMsgEnum;

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
