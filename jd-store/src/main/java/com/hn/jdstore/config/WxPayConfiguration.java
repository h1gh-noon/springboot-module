package com.hn.jdstore.config;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(WxPayService.class)
public class WxPayConfiguration {

    @Value("${mpAppId}")
    private String mpAppId;

    /**
     * 微信支付商户号
     */
    @Value("${mchId}")
    private String mchId;

    /**
     * 微信支付商户密钥
     */
    @Value("${mchKey}")
    private String mchKey;

    /**
     * 微信支付商户密钥
     */
    @Value("${keyPath}")
    private String keyPath;

    /**
     * 微信支付商户密钥
     */
    @Value("${notifyUrl}")
    private String notifyUrl;


    @Bean
    @ConditionalOnMissingBean
    public WxPayService wxService() {
        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setAppId(StringUtils.trimToNull(mpAppId));
        payConfig.setMchId(StringUtils.trimToNull(mchId));
        payConfig.setMchKey(StringUtils.trimToNull(mchKey));
        payConfig.setKeyPath(StringUtils.trimToNull(keyPath));
        payConfig.setNotifyUrl(notifyUrl);

        // 可以指定是否使用沙箱环境
        payConfig.setUseSandboxEnv(false);

        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(payConfig);
        return wxPayService;
    }
}
