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
import com.hn.user.domain.entity.UserDo;
import com.hn.user.domain.request.LoginRequest;
import com.hn.user.domain.request.UserListRequest;
import com.hn.user.domain.vo.LoginInfoVo;
import com.hn.user.domain.vo.UserVo;
import com.hn.user.mapper.UserMapper;
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
    public UserDo getUserById(Long id) {
        return userMapper.getUserById(id);
    }

    @Override
    public UserDo getUserByName(String username) {
        return userMapper.getUserByName(username);
    }

    @Override
    public PaginationData<List<UserDto>> getUserPageList(Integer currentPage, Integer pageSize,
                                                         String sort,
                                                         UserListRequest userListQuery) throws IllegalAccessException {
        PaginationData<List<UserDto>> paginationData = new PaginationData<>();
        Integer limitRows = (currentPage - 1) * pageSize;
        Map<String, Object> maps = Util.getObjectToMap(userListQuery);

        if (sort != null) {
            String[] sorts = sort.split("-");
            maps.put("sortField", sorts[0]);
            maps.put("sort", sorts[1]);
        }
        maps.put("limitRows", limitRows);
        maps.put("pageSize", pageSize);
        Util.removeNonValue(maps);
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
        UserDo user = userMapper.getUserByName(username);
        return user == null ? 0 : 1;
    }

    @Override
    public int userAdd(UserDto userDto) {
        if (hasUserByName(userDto.getUsername()) > 0) {
            // 用户名已存在
            return 0;
        }
        userDto.setPassword(PBKDF2Util.encode(userDto.getPassword()));
        UserDo userDo = new UserDo();
        BeanUtils.copyProperties(userDto, userDo);
        int n = userMapper.userAdd(userDo);
        userDto.setId(userDo.getId());
        return n;
    }

    @Override
    public int userUpdate(UserDto userDto) throws TemplateException {
        if (userDto.getUsername() != null) {
            UserDo u = userMapper.getUserByName(userDto.getUsername());
            if (!Objects.equals(u.getId(), userDto.getId())) {
                // 修改用户名 且 目标用户名已存在
                throw new TemplateException(ResponseEnum.HAS_USERNAME);
            }
        }
        if (userDto.getPassword() != null) {
            userDto.setPassword(PBKDF2Util.encode(userDto.getPassword()));
        }
        UserDo user = new UserDo();
        BeanUtils.copyProperties(userDto, user);
        return userMapper.userUpdate(user);
    }

    @Override
    public int userUpdateStatus(UserDto userDto) {
        UserDo userDo = new UserDo();
        userDo.setId(userDto.getId());
        userDo.setUsername(userDto.getUsername());
        userDo.setStatus(userDto.getStatus());
        int n = userMapper.userUpdateStatus(userDo);
        // 从redis中删除
        redisRemoveUser(RedisConstant.USER_TOKEN_LIST + userDo.getId());
        return n;
    }

    @Override
    public int userDelete(UserDto userDto) {
        UserDo userDo = new UserDo();
        userDo.setId(userDto.getId());
        int n = userMapper.userDelete(userDo);
        // 从redis中删除
        redisRemoveUser(RedisConstant.USER_TOKEN_LIST + userDo.getId());
        return n;
    }

    public void redisRemoveUser(String userKey) {
        List<String> range =
                stringRedisTemplate.opsForList().range(userKey, 0, -1);

        if (range != null) {
            range.forEach(e -> {
                stringRedisTemplate.delete(e);
            });
            stringRedisTemplate.delete(userKey);
        }
    }

    @Override
    public LoginInfoVo userLogin(LoginRequest loginRequest) {

        UserDo user = getUserByName(loginRequest.getUsername());
        if (!user.getStatus().equals(0)) {
            if (PBKDF2Util.verification(loginRequest.getPassword(), user.getPassword())) {
                // 验证通过 清空密码
                user.setPassword(null);
                String token = setUserToken(user);
                LoginInfoVo loginInfoVo = new LoginInfoVo();

                UserVo userVo = new UserVo();
                BeanUtils.copyProperties(user, userVo);
                loginInfoVo.setUserInfo(userVo);
                loginInfoVo.setToken(token);
                return loginInfoVo;

            }
        }
        return null;
    }

    @Override
    public String setUserToken(UserDo user) {
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
                operations.opsForValue().set(RedisConstant.USER_TOKEN + token,
                        JSON.toJSONString(user));
                operations.expire(RedisConstant.USER_TOKEN + token, 3600 * 24, TimeUnit.SECONDS);
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


    public UserDo getUserByToken(String token) {
        if (Strings.isEmpty(token)) {
            return null;
        }
        Object obj = redisUtil.get(RedisConstant.USER_TOKEN + token);
        if (obj != null) {
            return JSON.parseObject((String) obj, UserDo.class);
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
