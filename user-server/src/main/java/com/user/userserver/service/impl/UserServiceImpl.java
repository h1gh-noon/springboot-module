package com.user.userserver.service.impl;


import com.user.userserver.entity.User;
import com.user.userserver.mapper.UserMapper;
import com.user.userserver.model.UserInfo;
import com.user.userserver.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserInfo getUserById(Long id) {

        User u = userMapper.getUserById(id);

        UserInfo userInfo = new UserInfo();
        if (u != null) {
            BeanUtils.copyProperties(u, userInfo);
        }
        return userInfo;
    }

    @Override
    public List<User> getUserPageList(Map<String, Object> map) {
        return userMapper.getUserPageList(map);
    }

    @Override
    public Long userCount(Map<String, Object> map) {
        return userMapper.userCount(map);
    }


    @Override
    public int userAdd(User user) {
        return userMapper.userAdd(user);
    }

    @Override
    public int userUpdate(User user) {
        return userMapper.userUpdate(user);
    }

    @Override
    public int userUpdateStatus(User user) {
        return userMapper.userUpdateStatus(user);
    }

    @Override
    public int userDelete(User user) {
        return userMapper.userDelete(user);
    }
}
