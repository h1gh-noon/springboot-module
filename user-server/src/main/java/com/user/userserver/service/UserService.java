package com.user.userserver.service;


import com.user.userserver.entity.UserEntity;
import com.user.userserver.model.PaginationData;
import com.user.userserver.model.UserModel;

import java.util.List;
import java.util.Map;

public interface UserService {

    UserEntity getUserById(Long id);

    UserEntity getUserByName(String username);

    UserEntity getUserByToken(String token);

    boolean authUserByToken(String token);

    boolean loginOut(String token, UserEntity user);

    boolean loginOutAll(String toke, UserEntity user);


    PaginationData<List<UserEntity>> getUserPageList(Map<String, Object> map);

    int hasUserByName(String username);

    int userAdd(UserEntity user);

    int userUpdate(UserEntity user);

    int userUpdateStatus(UserEntity user);

    int userDelete(UserEntity user);

    String userLogin(UserModel userModel);

}
