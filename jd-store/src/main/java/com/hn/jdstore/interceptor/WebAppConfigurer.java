package com.hn.jdstore.interceptor;

import com.hn.common.interceptor.AuthInterceptor;
import com.hn.common.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {

    @Bean
    public AuthInterceptor authFilter() {
        return new AuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(authFilter()).addPathPatterns("/**").order(0);

        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").order(1);
    }
}
