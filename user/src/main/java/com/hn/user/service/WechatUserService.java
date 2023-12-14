package com.hn.user.service;

import com.hn.user.domain.request.WechatUserInfoRequest;

public interface WechatUserService {

    String wechatUserLogin(WechatUserInfoRequest userInfoDto);

}
