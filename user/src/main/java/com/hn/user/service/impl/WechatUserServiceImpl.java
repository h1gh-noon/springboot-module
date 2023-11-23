package com.hn.user.service.impl;

import com.hn.user.dto.WechatUserInfoDto;
import com.hn.user.entity.UserEntity;
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
    public String wechatUserLogin(WechatUserInfoDto userInfoDto) {

        UserEntity userEntity = userMapper.getUserByOpenid(userInfoDto.getOpenid());

        if (userEntity == null) {
            // 新增
            userEntity = new UserEntity();
            BeanUtils.copyProperties(userInfoDto, userEntity);
            userEntity.setPassword("");
            userEntity.setUsername(UUID.randomUUID().toString().replace("-", ""));
            userMapper.userAdd(userEntity);
        } else {
            userEntity.setPassword(null);
        }
        return userService.setUserToken(userEntity);
    }
}
