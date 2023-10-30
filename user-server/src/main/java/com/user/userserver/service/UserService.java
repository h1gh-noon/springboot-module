package com.user.userserver.service;


import com.user.userserver.entity.User;
import com.user.userserver.model.UserInfo;

import java.util.List;
import java.util.Map;

public interface UserService {

    UserInfo getUserById(Long id);

    User getUserByName(String username);

    List<User> getUserPageList(Map<String, Object> map);

    Long userCount(Map<String, Object> map);

    int hasUserByName(String username);

    int userAdd(User user);

    int userUpdate(User user);

    int userUpdateStatus(User user);

    int userDelete(User user);

    String userLogin(Map<String, String> map);

}
