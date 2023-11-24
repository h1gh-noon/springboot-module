package com.hn.user.aspect;

import com.alibaba.fastjson2.JSON;
import com.hn.common.constant.RequestHeaderConstant;
import com.hn.common.enums.ResponseEnum;
import com.hn.common.exceptions.TemplateException;
import com.hn.user.entity.UserEntity;
import com.hn.user.enums.PermissionsEnum;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class PermissionsAspect {

    // @Resource
    // private UrlWhiteList urlWhiteList;

    private static final PathMatcher pathMatcher = new AntPathMatcher();

    @Pointcut("execution(public * com.hn.user.controller.UserController.*(..))")
    private void userPermission() {
    }

    @Around("userPermission()")
    private Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        log.info("user...");
        // 获取request
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String headerInfo = request.getHeader(RequestHeaderConstant.HEADER_TOKEN_INFO);
        String requestUri = request.getRequestURI();
        log.info("url={} header-token信息={}", requestUri, headerInfo);
        // System.out.println(urlWhiteList);
        // if (headerInfo == null) {
        //     for (String s : urlWhiteList.getWhiteList()) {
        //         if (pathMatcher.match(s, requestUri)) {
        //             return pjp.proceed();
        //         }
        //     }
        // } else {
        // 手动白名单
        if (pathMatcher.match("/**/auth/hasUserByName", requestUri)){
            return pjp.proceed();
        }
        // 验证权限
        UserEntity user = JSON.parseObject(headerInfo, UserEntity.class);
        String permissions = user.getPermissions();
        if (permissions != null && !permissions.isEmpty()) {
            if (Arrays.stream(permissions.split(",")).anyMatch(e -> e.equals(PermissionsEnum.SUPER_ADMIN.getName()))) {
                return pjp.proceed();
            }
        }

        // }

        // 失败 401
        throw new TemplateException(ResponseEnum.FAIL_401);

    }
}
