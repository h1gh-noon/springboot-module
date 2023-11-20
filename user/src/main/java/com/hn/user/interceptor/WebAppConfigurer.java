package com.hn.user.interceptor;

import com.hn.common.interceptor.AuthInterceptor;
import com.hn.common.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Bean
    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(authInterceptor())
                .addPathPatterns(contextPath + "/**")
                .excludePathPatterns(contextPath + "/auth/userLogin").order(0);

        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns(contextPath + "/**")
                .excludePathPatterns(contextPath + "/auth/userLogin")
                .order(1);

    }

}
