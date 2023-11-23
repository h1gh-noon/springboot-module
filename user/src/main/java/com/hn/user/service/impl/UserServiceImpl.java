package com.hn.user.service.impl;


import com.alibaba.fastjson2.JSON;
import com.hn.common.api.PaginationData;
import com.hn.common.constant.RedisConstant;
import com.hn.common.dto.UserDto;
import com.hn.common.enums.ResponseEnum;
import com.hn.common.exceptions.TemplateException;
import com.hn.common.util.PBKDF2Util;
import com.hn.common.util.RedisUtil;
import com.hn.common.util.Util;
import com.hn.user.dto.LoginDto;
import com.hn.user.entity.UserEntity;
import com.hn.user.mapper.UserMapper;
import com.hn.user.model.LoginInfoModel;
import com.hn.user.model.UserModel;
import com.hn.user.service.UserService;
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
    public PaginationData<List<UserDto>> getUserPageList(Integer currentPage, Integer pageSize, UserDto userDto) throws IllegalAccessException {
        PaginationData<List<UserDto>> paginationData = new PaginationData<>();
        Integer limitRows = (currentPage - 1) * pageSize;
        Map<String, Object> maps = Util.getObjectToMap(userDto);
        maps.put("limitRows", limitRows);
        maps.put("pageSize", pageSize);

        paginationData.setData(userMapper.getUserPageList(maps).stream().map(e -> {
            UserDto u = new UserDto();
            BeanUtils.copyProperties(e, u);
            return u;
        }).collect(Collectors.toList()));
        paginationData.setTotal(userMapper.userCount(maps));
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
    public LoginInfoModel userLogin(LoginDto loginDto) {
        UserEntity user = getUserByName(loginDto.getUsername());
        if (PBKDF2Util.verification(loginDto.getPassword(), user.getPassword())) {
            // 验证通过 清空密码
            user.setPassword(null);
            String token = setUserToken(user);
            LoginInfoModel loginInfoModel = new LoginInfoModel();

            UserModel userModel = new UserModel();
            BeanUtils.copyProperties(user, userModel);
            loginInfoModel.setUserInfo(userModel);
            loginInfoModel.setToken(token);
            return loginInfoModel;

        }
        return null;
    }

    @Override
    public String setUserToken(UserEntity user) {
        String token = Util.getRandomToken();
        List<String> list = new ArrayList<>();
        list.add(RedisConstant.USER_TOKEN + token);

        String userKey = RedisConstant.USER_TOKEN_LIST + user.getId();
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
                operations.opsForValue().set(RedisConstant.USER_TOKEN + token, JSON.toJSONString(user));
                operations.expire(RedisConstant.USER_TOKEN + token, 3600 * 12, TimeUnit.SECONDS);
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


    public UserEntity getUserByToken(String token) {
        if (Strings.isEmpty(token)) {
            return null;
        }
        Object obj = redisUtil.get(RedisConstant.USER_TOKEN + token);
        if (obj != null) {
            return JSON.parseObject((String) obj, UserEntity.class);
        }
        return null;
    }

    @Override
    public boolean authUserByToken(String token) {
        return getUserByToken(RedisConstant.USER_TOKEN + token) != null;
    }

    @Override
    public boolean loginOut(String token, UserDto userDto) {
        String userKey = RedisConstant.USER_TOKEN_LIST + userDto.getId();
        // 开启事务
        SessionCallback<Object> callback = new SessionCallback<>() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                operations.delete(RedisConstant.USER_TOKEN + token);
                operations.opsForList().remove(userKey, 1, RedisConstant.USER_TOKEN + token);
                return operations.exec();
            }
        };
        stringRedisTemplate.execute(callback);
        return true;
    }

    @Override
    public boolean loginOutAll(UserDto userDto) {
        String userKey = RedisConstant.USER_TOKEN_LIST + userDto.getId();
        List<String> removeKeys = new ArrayList<>();
        removeKeys.add(userKey);
        removeKeys.addAll(redisUtil.lRange(userKey));

        return redisUtil.del(removeKeys);
    }
}
