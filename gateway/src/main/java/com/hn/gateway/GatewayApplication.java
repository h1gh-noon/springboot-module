package com.hn.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(value = {"com.hn"}, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX,
                pattern = {"com.hn.common.interceptor.*"})})
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
