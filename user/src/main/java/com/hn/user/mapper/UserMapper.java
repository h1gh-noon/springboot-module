package com.hn.user.mapper;

import com.hn.user.domain.entity.UserDo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    UserDo getUserById(Long id);

    UserDo getUserByName(String username);

    UserDo getUserByOpenid(String openid);

    List<UserDo> getUserPageList(Map<String, Object> map);

    Long userCount(Map<String, Object> map);

    int userAdd(UserDo user);

    int userUpdate(UserDo user);

    int userUpdateStatus(UserDo user);

    int userDelete(UserDo user);

}
