package com.hn.common.interceptor;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {

    // @Bean
    // public AuthInterceptor authInterceptor() {
    //     return new AuthInterceptor();
    // }

    @Resource
    private ObjectMapper objectMapper;

    @PostConstruct
    public void EnumObjectMapper() {
        // 解决enum不匹配问题 默认值为false
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    }


    // @Override
    // public void addInterceptors(InterceptorRegistry registry) {
    //
    //     // registry.addInterceptor(authInterceptor())
    //     //         .addPathPatterns(contextPath + "/**")
    //     //         .excludePathPatterns(contextPath + "/auth/userLogin").order(0);
    //
    //     registry.addInterceptor(new LoginInterceptor())
    //             .addPathPatterns("/api/**")
    //             .excludePathPatterns("/auth/userLogin")
    //             .order(1);
    //
    // }

}
