package com.user.userserver.service;


import com.user.userserver.entity.User;
import com.user.userserver.model.UserInfo;

import java.util.List;
import java.util.Map;

public interface UserService {

    UserInfo getUserById(Long id);

    List<User> getUserPageList(Map<String, Object> map);

    Long userCount(Map<String, Object> map);

    int userAdd(User user);

    int userUpdate(User user);

    int userUpdateStatus(User user);

    int userDelete(User user);

}
