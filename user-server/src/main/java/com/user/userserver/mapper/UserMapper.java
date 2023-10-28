package com.user.userserver.mapper;

import com.user.userserver.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    User getUserById(Long id);

    List<User> getUserPageList(Map<String, Object> map);

    Long userCount(Map<String, Object> map);


    int userAdd(User user);

    int userUpdate(User user);

    int userUpdateStatus(User user);

    int userDelete(User user);

}
