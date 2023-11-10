package com.user.userserver.service.impl;


import com.alibaba.fastjson2.JSON;
import com.user.userserver.entity.UserEntity;
import com.user.userserver.mapper.UserMapper;
import com.user.userserver.model.PaginationData;
import com.user.userserver.model.UserModel;
import com.user.userserver.service.UserService;
import com.user.userserver.util.PBKDF2Util;
import com.user.userserver.util.RedisUtil;
import com.user.userserver.util.Util;
import jakarta.annotation.Resource;
import org.apache.logging.log4j.util.Strings;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public static final String REDIS_USER_ID = "TOKEN_LIST_USER_ID_"; // 前缀(+用户id) redis存储每个用户的token列表key

    public static final Long REDIS_USER_MAX_TOKEN_COUNT = 5L; // 用户登录的最多设备数量

    @Override
    public UserEntity getUserById(Long id) {

        return userMapper.getUserById(id);

    }

    @Override
    public UserEntity getUserByName(String username) {
        return userMapper.getUserByName(username);
    }

    @Override
    public PaginationData<List<UserEntity>> getUserPageList(Map<String, Object> map) {
        PaginationData<List<UserEntity>> paginationData = new PaginationData<>();
        paginationData.setData(userMapper.getUserPageList(map));
        paginationData.setTotal(userMapper.userCount(map));
        return paginationData;
    }

    @Override
    public int hasUserByName(String username) {
        UserEntity user = userMapper.getUserByName(username);
        return user == null ? 0 : 1;
    }

    @Override
    public int userAdd(UserEntity user) {
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
    public int userUpdate(UserEntity user) {
        if (user.getPassword() != null) {
            user.setPassword(PBKDF2Util.encode(user.getPassword()));
        }
        if (user.getUsername() != null) {
            UserEntity u = userMapper.getUserByName(user.getUsername());
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
    public int userUpdateStatus(UserEntity user) {
        return userMapper.userUpdateStatus(user);
    }

    @Override
    public int userDelete(UserEntity user) {
        return userMapper.userDelete(user);
    }

    @Override
    public String userLogin(UserModel userModel) {
        UserEntity user = getUserByName(userModel.getUsername());
        if (PBKDF2Util.verification(userModel.getPassword(), user.getPassword())) {
            String token = Util.getRandomToken();
            List<String> list = new ArrayList<>();
            list.add(token);

            String userKey = REDIS_USER_ID + user.getId();
            Long tokenCount = stringRedisTemplate.opsForList().size(userKey);
            String oldToken = null;
            if (tokenCount != null && REDIS_USER_MAX_TOKEN_COUNT <= tokenCount) {
                // 控制每个用户最多登录的设备数
                oldToken = stringRedisTemplate.opsForList().leftPop(userKey);
            }

            // 开启事务
            String finalOldToken = oldToken;
            SessionCallback<Object> callback = new SessionCallback<>() {
                @Override
                public Object execute(RedisOperations operations) throws DataAccessException {
                    operations.multi();
                    operations.opsForValue().set(token, JSON.toJSONString(user));
                    operations.expire(token, 3600 * 12, TimeUnit.SECONDS);
                    operations.opsForList().rightPushAll(userKey, list);
                    if (finalOldToken != null) {
                        operations.delete(finalOldToken);
                    }
                    return operations.exec();
                }
            };

            stringRedisTemplate.execute(callback);

            return token;

        }
        return null;
    }

    public UserEntity getUserByToken(String token) {
        if (Strings.isEmpty(token)) {
            return null;
        }
        Object obj = redisUtil.get(token);
        if (obj != null) {
            return JSON.parseObject((String) obj, UserEntity.class);
        }
        return null;
    }

    @Override
    public boolean authUserByToken(String token) {
        return getUserByToken(token) != null;
    }

    @Override
    public boolean loginOut(String token, UserEntity user) {
        String userKey = REDIS_USER_ID + user.getId();
        // 开启事务
        SessionCallback<Object> callback = new SessionCallback<>() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                operations.delete(token);
                operations.opsForList().remove(userKey, 1, token);
                return operations.exec();
            }
        };
        stringRedisTemplate.execute(callback);
        return true;
    }

    @Override
    public boolean loginOutAll(String token, UserEntity user) {
        String userKey = REDIS_USER_ID + user.getId();
        List<String> removeKeys = new ArrayList<>();
        removeKeys.add(userKey);
        removeKeys.addAll(redisUtil.lRange(userKey));

        return redisUtil.del(removeKeys);
    }
}
