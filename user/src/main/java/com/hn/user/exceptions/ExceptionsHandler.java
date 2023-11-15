package com.hn.user.exceptions;


import com.hn.user.model.CommonResponse;
import com.hn.user.util.ResponseTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionsHandler {

    /**
     * 自定义异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(TemplateException.class)
    public TemplateException handleTemplateException(TemplateException e) {
        log.error(e.getMsg());
        e.printStackTrace();
        return e;
    }

    /**
     * exception异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public CommonResponse handleException(Exception e) {
        log.error(e.getMessage());
        e.printStackTrace();
        return ResponseTool.getErrorResponse();
    }

}
