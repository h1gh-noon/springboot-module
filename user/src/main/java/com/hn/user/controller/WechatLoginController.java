package com.hn.user.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.hn.common.util.ResponseTool;
import com.hn.user.dto.WechatUserInfoDto;
import com.hn.user.service.WechatUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Tag(name = "Wechat 微信授权", description = "微信授权")
@Controller
@RequestMapping("/authByWechat")
@RefreshScope
@Slf4j
public class WechatLoginController {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private WechatUserService wechatUserService;

    @Value("${Wechat.mpAppId}")
    private String mpAppId;

    @Value("${Wechat.mpAppSecret}")
    private String mpAppSecret;

    @Value("${Wechat.openAppId}")
    private String openAppId;

    @Value("${Wechat.openAppSecret}")
    private String openAppSecret;

    @Value("${Wechat.domainName}")
    private String domainName;

    @Operation(summary = "微信客户端授权引导url", description = "微信客户端授权引导url")
    @RequestMapping("/loginByWechatClientUrl")
    public String loginByWechatClientUrl() throws UnsupportedEncodingException {
        String redirect_uri = "http://" + domainName + "/api/user/authByWechat/loginByWechatClient";
        String encoderUri = URLEncoder.encode(redirect_uri, "GBK");

        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri" +
                "=%s&response_type=%s&scope=%s#wechat_redirect";

        return "redirect:" + String.format(url, mpAppId, encoderUri, "code", "snsapi_userinfo");
    }

    @Operation(summary = "微信客户端授权url", description = "微信客户端授url")
    @RequestMapping("/loginByWechatClient")
    public Object loginByWechatClient(@RequestParam String code) {

        String urlBuild = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type" +
                "=authorization_code";
        String url = String.format(urlBuild, mpAppId, mpAppSecret, code);
        String res = restTemplate.getForObject(url, String.class);
        log.info("微信客户端授权 Wechat接口返回={}", res);
        return loginByWechat(res);
    }

    @Operation(summary = "微信扫码授权引导url", description = "微信扫码授权引导url")
    @RequestMapping("/loginByWechatQRCodeUrl")
    public String loginByWechatQRCodeUrl() throws UnsupportedEncodingException {
        String redirect_uri = "http://" + domainName + "/api/user/authByWechat/loginByWechatQRCode";
        String encoderUri = URLEncoder.encode(redirect_uri, "GBK");

        String url = "https://open.weixin.qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&response_type" +
                "=code&scope=snsapi_login#wechat_redirect";

        return "redirect:" + String.format(url, openAppId, encoderUri);
    }

    @Operation(summary = "微信扫码授权url", description = "微信扫码授url")
    @RequestMapping("/loginByWechatQRCode")
    public Object loginByWechatQRCode(@RequestParam String code) {

        String urlBuild = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type" +
                "=authorization_code";
        String url = String.format(urlBuild, openAppId, openAppSecret, code);

        String res = restTemplate.getForObject(url, String.class);
        log.info("微信扫码授权 Wechat接口返回={}", res);
        return loginByWechat(res);
    }

    private Object loginByWechat(String result) {
        JSONObject resObj = JSON.parseObject(result);
        if (resObj != null && !resObj.containsKey("errcode")) {
            // success
            log.info("获取微信返回信息={}", resObj);
            String accToken = resObj.getString("access_token");
            String openid = resObj.getString("openid");
            String getUserInfoBuild = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";

            WechatUserInfoDto userInfoDto = JSON.parseObject(
                    restTemplate.getForObject(String.format(getUserInfoBuild,
                            accToken, openid), String.class), WechatUserInfoDto.class);

            if (userInfoDto != null && userInfoDto.getErrcode() == null) {
                // success
                String token = wechatUserService.wechatUserLogin(userInfoDto);
                return "redirect:http://localhost:5173/login?token=" + token;
            }

        }
        return ResponseTool.getErrorResponse();
    }
}
