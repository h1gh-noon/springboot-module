package com.hn.common.exceptions;


import com.hn.common.api.CommonResponse;
import com.hn.common.util.ResponseTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionsHandler {

    /**
     * 全局自定义异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(TemplateException.class)
    public CommonResponse handleTemplateException(TemplateException e) {
        log.error(e.getMsg());
        e.printStackTrace();
        return new CommonResponse(e.getCode(), e.getSuccess(), null, e.getMsg());
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
