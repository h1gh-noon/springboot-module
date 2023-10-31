package com.user.userserver.service.impl;


import com.alibaba.fastjson2.JSON;
import com.user.userserver.entity.User;
import com.user.userserver.mapper.UserMapper;
import com.user.userserver.model.PaginationData;
import com.user.userserver.model.UserInfo;
import com.user.userserver.service.UserService;
import com.user.userserver.util.PBKDF2Util;
import com.user.userserver.util.RedisUtil;
import jakarta.annotation.Resource;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisUtil redisUtil;

    public static final String REDIS_USER_ID = "TOKEN_LIST_USER_ID_"; // 前缀(+用户id) redis存储每个用户的token列表key

    public static final Long REDIS_USER_MAX_TOKEN_COUNT = 5L; // 用户登录的最多设备数量

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
    public PaginationData<List<UserInfo>> getUserPageList(Map<String, Object> map) {
        PaginationData<List<UserInfo>> paginationData = new PaginationData<>();
        paginationData.setData(userMapper.getUserPageList(map));
        paginationData.setTotal(userMapper.userCount(map));
        return paginationData;
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

            // 开启事务
            // redisUtil.MULTI();
            try {
                redisUtil.set(token, JSON.toJSONString(user));
                redisUtil.expire(token, 3600 * 24);
                String userKey = REDIS_USER_ID + user.getId();
                List<String> list = new ArrayList<>();
                list.add(token);
                redisUtil.rPush(userKey, list);
                Long tokenCount = redisUtil.lLen(userKey);
                if (tokenCount > REDIS_USER_MAX_TOKEN_COUNT) {
                    // int a = 0;
                    // System.out.println(10 / a);
                    // 控制每个用户最多登录的设备数
                    String oldToken = redisUtil.lPop(userKey);
                    redisUtil.del(oldToken);
                }
                // 提交事务
                // redisUtil.EXEC();
                return token;
            } catch (Exception e) {
                // 回滚
                // redisUtil.DISCARD();
            }

        }
        return null;
    }

    // 返回UserInfo 不包含密码等信息 对外提供
    @Override
    public UserInfo getUserInfoByToken(String token) {
        if (Strings.isEmpty(token)) {
            return null;
        }
        Object obj = redisUtil.get(token);
        if (obj != null) {
            return JSON.parseObject((String) obj, UserInfo.class);
        }
        return null;
    }

    // 返回User 包含密码等信息 对内提供
    public User getUserByToken(String token) {
        if (Strings.isEmpty(token)) {
            return null;
        }
        Object obj = redisUtil.get(token);
        if (obj != null) {
            return JSON.parseObject((String) obj, User.class);
        }
        return null;
    }

    @Override
    public boolean authUserByToken(String token) {
        return getUserByToken(token) != null;
    }
}
