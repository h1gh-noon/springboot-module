package com.user.userserver.service;


import com.user.userserver.entity.User;
import com.user.userserver.model.UserInfo;

import java.util.List;

public interface UserService {

    UserInfo getUserById(Long id);

    List<User> getUserPageList(User user);

    int userAdd(User user);

    int userUpdate(User user);

    int userUpdateStatus(User user);

    int userDelete(User user);

}
