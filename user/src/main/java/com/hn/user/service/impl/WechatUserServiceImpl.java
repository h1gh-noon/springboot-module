package com.hn.user.service.impl;

import com.hn.user.domain.request.WechatUserInfoRequest;
import com.hn.user.domain.entity.UserDo;
import com.hn.user.mapper.UserMapper;
import com.hn.user.service.UserService;
import com.hn.user.service.WechatUserService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WechatUserServiceImpl implements WechatUserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserService userService;

    @Override
    public String wechatUserLogin(WechatUserInfoRequest userInfoDto) {

        UserDo userDo = userMapper.getUserByOpenid(userInfoDto.getOpenid());

        if (userDo == null) {
            // 新增
            userDo = new UserDo();
            BeanUtils.copyProperties(userInfoDto, userDo);
            userDo.setPassword("");
            userDo.setUsername(UUID.randomUUID().toString().replace("-", ""));
            userMapper.userAdd(userDo);
        } else {
            userDo.setPassword(null);
        }
        return userService.setUserToken(userDo);
    }
}
