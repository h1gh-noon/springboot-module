package com.hn.user.service;

import com.hn.user.dto.WechatUserInfoDto;

public interface WechatUserService {

    String wechatUserLogin(WechatUserInfoDto userInfoDto);

}
