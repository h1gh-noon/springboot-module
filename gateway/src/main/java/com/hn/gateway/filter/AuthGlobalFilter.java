package com.hn.gateway.filter;

import com.hn.common.constant.RedisConstant;
import com.hn.common.constant.RequestHeaderConstant;
import com.hn.common.util.RedisUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 *
 */
@Slf4j
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Resource
    private RedisUtil redisUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {


        String token = exchange.getRequest().getHeaders().getFirst(RequestHeaderConstant.HEADER_TOKEN);
        if (token == null || token.isEmpty()) {
            ServerHttpRequest request =
                    exchange.getRequest().mutate().header(RequestHeaderConstant.HEADER_TOKEN_INFO).build();
            exchange = exchange.mutate().request(request).build();
            return chain.filter(exchange);
        }
        // 验证token 获取用户信息 设置到Header中
        ServerHttpRequest request = null;
        Object o = redisUtil.get(RedisConstant.USER_TOKEN + token);
        if (o == null) {
            request = exchange.getRequest().mutate().header(RequestHeaderConstant.HEADER_TOKEN)
                    .header(RequestHeaderConstant.HEADER_TOKEN_INFO).build();
        } else {
            String tokenInfo = (String) o;
            log.info("AuthGlobalFilter.filter() user:{}", tokenInfo);
            request = exchange.getRequest().mutate().header(RequestHeaderConstant.HEADER_TOKEN_INFO,
                    tokenInfo).build();
        }
        exchange = exchange.mutate().request(request).build();
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
