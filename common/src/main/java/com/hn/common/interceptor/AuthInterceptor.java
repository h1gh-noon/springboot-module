package com.hn.common.interceptor;

import com.hn.common.constant.RedisConstant;
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

public class AuthInterceptor implements HandlerInterceptor {

    @Resource
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader(RequestHeaderConstant.HEADER_TOKEN);
        Map<String, String> header = new HashMap<>();
        if (Strings.isEmpty(token)) {
            header.put(RequestHeaderConstant.HEADER_TOKEN, null);
            header.put(RequestHeaderConstant.HEADER_TOKEN_INFO, null);
        } else {
            Object o = redisUtil.get(RedisConstant.USER_TOKEN + token);
            if (o == null) {
                header.put(RequestHeaderConstant.HEADER_TOKEN, null);
                header.put(RequestHeaderConstant.HEADER_TOKEN_INFO, null);
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
    private void modifyHeaders(HttpServletRequest request, Map<String, String> headers) throws NoSuchFieldException,
            IllegalAccessException {
        if (headers == null || headers.isEmpty()) {
            return;
        }

        // 添加依赖 springdoc-openapi-starter-webmvc-ui(knife4j-openapi3-jakarta-spring-boot-starter的子依赖) 后
        // 会影响request 的headers层级(继承过来的 使用反射修改值需要getSuperclass()到对应的super类上 )
        // 猜测可能跟OpenAPI3规范有关
        Class<?> superRequestClass = request.getClass().getSuperclass().getSuperclass();
        Field requestF = superRequestClass.getDeclaredField("request");
        requestF.setAccessible(true);
        Object requestO = requestF.get(request);

        Field requestField = requestO.getClass().getDeclaredField("request");
        requestField.setAccessible(true);
        Object requestObj = requestField.get(requestO);

        Field coyoteRequest = requestObj.getClass().getDeclaredField("coyoteRequest");
        coyoteRequest.setAccessible(true);
        Object coyoteRequestObj = coyoteRequest.get(requestObj);

        Field headersField = coyoteRequestObj.getClass().getDeclaredField("headers");
        headersField.setAccessible(true);
        MimeHeaders mimeHeaders = (MimeHeaders) headersField.get(coyoteRequestObj);
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            mimeHeaders.removeHeader(entry.getKey());
            if (entry.getValue() != null) {
                mimeHeaders.addValue(entry.getKey()).setString(entry.getValue());
            }
        }
    }

}
