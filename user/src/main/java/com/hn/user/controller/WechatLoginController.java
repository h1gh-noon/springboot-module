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

    @Value("${Wechat.domainName}")
    private String domainName;

    @Operation(summary = "微信授权引导url", description = "微信授权引导url")
    @RequestMapping("/loginByWechatUrl")
    public String loginByWechatUrl() throws UnsupportedEncodingException {
        String redirect_uri = "http://"+ domainName +"/api/user/authByWechat/loginByWechat";
        String encoderUri = URLEncoder.encode(redirect_uri, "GBK");

        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri" +
                "=%s&response_type=%s&scope=%s#wechat_redirect";

        return "redirect:" + String.format(url, mpAppId, encoderUri, "code", "snsapi_userinfo");
    }

    @Operation(summary = "微信授权url", description = "微信授url")
    @RequestMapping("/loginByWechat")
    public Object loginByWechat(@RequestParam String code,
                                @RequestParam(required = false) String state) {

        log.info("code={}, state={}", code, state);

        String urlBuild = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type" +
                "=authorization_code";
        String url = String.format(urlBuild, mpAppId, mpAppSecret, code);
        String res = restTemplate.getForObject(url, String.class);
        JSONObject resObj = JSON.parseObject(res);
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
                return "redirect:http://192.168.110.179:5173/login?token=" + token;
            }

        }
        return ResponseTool.getErrorResponse();
    }
}
