package com.hn.common.filter;

import com.hn.common.constant.RequestHeaderConstant;
import com.hn.common.enums.ResponseEnum;
import com.hn.common.exceptions.TemplateException;
import com.hn.common.util.RedisUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.util.Strings;
import org.apache.tomcat.util.http.MimeHeaders;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class LoginFilter implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getHeader(RequestHeaderConstant.HEADER_TOKEN) == null) {
            throw new TemplateException(ResponseEnum.FAIL_401);
        }
        return true;
    }

}
