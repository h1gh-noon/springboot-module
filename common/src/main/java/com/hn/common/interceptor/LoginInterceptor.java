package com.hn.common.interceptor;

import com.hn.common.constant.RequestHeaderConstant;
import com.hn.common.enums.ResponseEnum;
import com.hn.common.exceptions.TemplateException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getHeader(RequestHeaderConstant.HEADER_TOKEN) == null) {
            throw new TemplateException(ResponseEnum.FAIL_401);
        }
        return true;
    }

}
