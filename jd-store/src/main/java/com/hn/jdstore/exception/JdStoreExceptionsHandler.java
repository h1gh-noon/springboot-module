package com.hn.jdstore.exception;


import com.hn.common.model.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.hn.jdstore")
@Slf4j
public class JdStoreExceptionsHandler {

    /**
     * 自定义异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(SelfException.class)
    public CommonResponse handleSelfException(SelfException e) {
        log.error(e.getMsg());
        e.printStackTrace();
        return new CommonResponse(e.getCode(), e.getSuccess(), null, e.getMsg());
    }

}
