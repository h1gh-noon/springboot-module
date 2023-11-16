package com.hn.jdstore.filter;

import com.hn.common.filter.AuthFilter;
import com.hn.common.filter.LoginFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new AuthFilter()).addPathPatterns("/**").order(0);

        registry.addInterceptor(new LoginFilter()).addPathPatterns("/**").order(1);
    }
}
