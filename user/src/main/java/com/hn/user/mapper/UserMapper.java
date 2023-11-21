package com.hn.user.mapper;

import com.hn.user.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    UserEntity getUserById(Long id);

    UserEntity getUserByName(String username);

    List<UserEntity> getUserPageList(Map<String, Object> map);

    Long userCount(Map<String, Object> map);

    int userAdd(UserEntity user);

    int userUpdate(UserEntity user);

    int userUpdateStatus(UserEntity user);

    int userDelete(UserEntity user);

}
