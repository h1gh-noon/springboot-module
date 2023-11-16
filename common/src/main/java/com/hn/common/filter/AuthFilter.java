package com.hn.common.filter;

import com.hn.common.constant.RequestHeaderConstant;
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

public class AuthFilter implements HandlerInterceptor {

    @Resource
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader(RequestHeaderConstant.HEADER_TOKEN);
        Map<String, String> header = new HashMap<>();
        if (Strings.isEmpty(token)) {
            header.put(RequestHeaderConstant.HEADER_TOKEN_INFO, "");
        } else {
            Object o = redisUtil.get(token);
            if (o == null) {
                header.put(RequestHeaderConstant.HEADER_TOKEN, "");
                header.put(RequestHeaderConstant.HEADER_TOKEN_INFO, "");
            } else {
                String tokenInfo = (String) o;
                header.put(RequestHeaderConstant.HEADER_TOKEN_INFO, tokenInfo);
            }
        }
        modifyHeaders(request, header);
        return true;
    }

    /**
     * 修改请求头
     *
     * @param headers
     * @param request
     */
    private void modifyHeaders(HttpServletRequest request, Map<String, String> headers) {
        if (headers == null || headers.isEmpty()) {
            return;
        }

        Class<? extends HttpServletRequest> requestClass = request.getClass();
        try {
            Field requestField = requestClass.getDeclaredField("request");
            requestField.setAccessible(true);
            Object requestObj = requestField.get(request);
            Field coyoteRequest = requestObj.getClass().getDeclaredField("coyoteRequest");
            coyoteRequest.setAccessible(true);
            Object coyoteRequestObj = coyoteRequest.get(requestObj);
            Field headersField = coyoteRequestObj.getClass().getDeclaredField("headers");
            headersField.setAccessible(true);
            MimeHeaders mimeHeaders = (MimeHeaders) headersField.get(coyoteRequestObj);
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                mimeHeaders.removeHeader(entry.getKey());
                mimeHeaders.addValue(entry.getKey()).setString(entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
