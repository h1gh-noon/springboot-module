package com.user.userserver.service.impl;


import com.user.userserver.entity.User;
import com.user.userserver.mapper.UserMapper;
import com.user.userserver.model.UserInfo;
import com.user.userserver.service.UserService;
import com.user.userserver.util.PBKDF2Util;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

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
    public User getUserByName(String username) {
        return userMapper.getUserByName(username);
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
    public int hasUserByName(String username) {
        User user = userMapper.getUserByName(username);
        return user == null ? 0 : 1;
    }

    @Override
    public int userAdd(User user) {
        if (hasUserByName(user.getUsername()) > 0) {
            // 用户名已存在
            return 0;
        }
        user.setPassword(PBKDF2Util.encode(user.getPassword()));
        int n = userMapper.userAdd(user);
        user.setPassword(null);
        return n;
    }

    @Override
    public int userUpdate(User user) {
        if (user.getPassword() != null) {
            user.setPassword(PBKDF2Util.encode(user.getPassword()));
        }
        if (user.getUsername() != null) {
            User u = userMapper.getUserByName(user.getUsername());
            if (!Objects.equals(u.getId(), user.getId())) {
                // 修改用户名 且 目标用户名已存在
                return 0;
            }
        }
        int n = userMapper.userUpdate(user);
        // 清空密文 返回到controller
        user.setPassword(null);
        return n;
    }

    @Override
    public int userUpdateStatus(User user) {
        return userMapper.userUpdateStatus(user);
    }

    @Override
    public int userDelete(User user) {
        return userMapper.userDelete(user);
    }

    @Override
    public String userLogin(Map<String, String> map) {
        User user = getUserByName(map.get("username"));
        if (PBKDF2Util.verification(map.get("password"), user.getPassword())) {
            String token = UUID.randomUUID().toString().replace("-", "");
            System.out.println(token);
            return token;
        }
        return null;
    }
}
