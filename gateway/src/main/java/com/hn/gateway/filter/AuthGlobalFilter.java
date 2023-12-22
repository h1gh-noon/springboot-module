package com.hn.gateway.filter;

import com.alibaba.fastjson2.JSON;
import com.hn.common.constant.RedisConstant;
import com.hn.common.constant.RequestHeaderConstant;
import com.hn.common.enums.ResponseEnum;
import com.hn.common.util.RedisUtil;
import com.hn.common.util.ResponseTool;
import com.hn.gateway.config.UrlWhiteList;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 *
 */
@Slf4j
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Resource
    private UrlWhiteList urlWhiteList;

    @Resource
    private RedisUtil redisUtil;

    private static final PathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        System.out.println(urlWhiteList);
        List<String> whiteList = urlWhiteList.getWhiteList();

        String path = exchange.getRequest().getURI().getPath();

        String token = exchange.getRequest().getHeaders().getFirst(RequestHeaderConstant.HEADER_TOKEN);
        if (Strings.isEmpty(token)) {
            // token为空 清空两个请求头
            ServerHttpRequest request =
                    exchange.getRequest().mutate().header(RequestHeaderConstant.HEADER_TOKEN)
                            .header(RequestHeaderConstant.HEADER_TOKEN_INFO).build();
            exchange = exchange.mutate().request(request).build();
        } else {
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

            // token正确放行
            if (o != null) {
                return chain.filter(exchange);
            }
        }

        // 白名单 放行
        for (String s : whiteList) {
            if (pathMatcher.match(s, path)) {
                return chain.filter(exchange);
            }
        }

        // 返回 401
        byte[] bytes = JSON.toJSONBytes(ResponseTool.getErrorResponse(ResponseEnum.FAIL_401));
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        return response.writeWith(Mono.just(buffer));

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
