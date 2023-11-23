package com.hn.gateway.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RefreshScope
@EqualsAndHashCode(callSuper = false)
@ConfigurationProperties(prefix = "white-url")
@Data
public class UrlWhiteList {

    private List<String> whiteList;
}
