package com.hn.user.service.impl;


import com.alibaba.fastjson2.JSON;
import com.hn.user.entity.UserEntity;
import com.hn.user.mapper.UserMapper;
import com.hn.user.service.UserService;
import com.hn.user.dto.UserDto;
import com.hn.user.enums.ResponseEnum;
import com.hn.user.exceptions.TemplateException;
import com.hn.user.model.PaginationData;
import com.hn.user.util.PBKDF2Util;
import com.hn.user.util.RedisUtil;
import com.hn.user.util.Util;
import jakarta.annotation.Resource;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
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
import java.util.stream.Collectors;

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
    public PaginationData<List<UserDto>> getUserPageList(Map<String, Object> map) {
        PaginationData<List<UserDto>> paginationData = new PaginationData<>();
        paginationData.setData(userMapper.getUserPageList(map).stream().map(e -> {
            UserDto u = new UserDto();
            BeanUtils.copyProperties(e, u);
            return u;
        }).collect(Collectors.toList()));
        paginationData.setTotal(userMapper.userCount(map));
        return paginationData;
    }

    @Override
    public int hasUserByName(String username) {
        UserEntity user = userMapper.getUserByName(username);
        return user == null ? 0 : 1;
    }

    @Override
    public int userAdd(UserDto userDto) {
        if (hasUserByName(userDto.getUsername()) > 0) {
            // 用户名已存在
            return 0;
        }
        userDto.setPassword(PBKDF2Util.encode(userDto.getPassword()));
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);
        int n = userMapper.userAdd(userEntity);
        userDto.setId(userEntity.getId());
        return n;
    }

    @Override
    public int userUpdate(UserDto userDto) throws TemplateException {
        if (userDto.getUsername() != null) {
            UserEntity u = userMapper.getUserByName(userDto.getUsername());
            if (!Objects.equals(u.getId(), userDto.getId())) {
                // 修改用户名 且 目标用户名已存在
                throw new TemplateException(ResponseEnum.HAS_USERNAME);
            }
        }
        if (userDto.getPassword() != null) {
            userDto.setPassword(PBKDF2Util.encode(userDto.getPassword()));
        }
        UserEntity user = new UserEntity();
        BeanUtils.copyProperties(userDto, user);
        return userMapper.userUpdate(user);
    }

    @Override
    public int userUpdateStatus(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDto.getId());
        userEntity.setUsername(userDto.getUsername());
        userEntity.setStatus(userDto.getStatus());
        return userMapper.userUpdateStatus(userEntity);
    }

    @Override
    public int userDelete(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDto.getId());
        return userMapper.userDelete(userEntity);
    }

    @Override
    public String userLogin(UserDto userDto) {
        UserEntity user = getUserByName(userDto.getUsername());
        if (PBKDF2Util.verification(userDto.getPassword(), user.getPassword())) {
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
    public boolean loginOut(String token, UserDto userDto) {
        String userKey = REDIS_USER_ID + userDto.getId();
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
    public boolean loginOutAll(String token, UserDto userDto) {
        String userKey = REDIS_USER_ID + userDto.getId();
        List<String> removeKeys = new ArrayList<>();
        removeKeys.add(userKey);
        removeKeys.addAll(redisUtil.lRange(userKey));

        return redisUtil.del(removeKeys);
    }
}
