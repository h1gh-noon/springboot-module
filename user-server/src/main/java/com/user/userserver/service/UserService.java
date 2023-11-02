package com.user.userserver.service;


import com.user.userserver.entity.User;
import com.user.userserver.model.PaginationData;
import com.user.userserver.model.UserInfo;

import java.util.List;
import java.util.Map;

public interface UserService {

    UserInfo getUserById(Long id);

    User getUserByName(String username);

    UserInfo getUserInfoByToken(String token);

    boolean authUserByToken(String token);

    boolean loginOut(String token, User user);

    boolean loginOutAll(String toke, User usern);


    PaginationData<List<UserInfo>> getUserPageList(Map<String, Object> map);

    int hasUserByName(String username);

    int userAdd(User user);

    int userUpdate(User user);

    int userUpdateStatus(User user);

    int userDelete(User user);

    String userLogin(Map<String, String> map);

}
