package com.hn.user.service;


import com.hn.common.api.PaginationData;
import com.hn.common.dto.UserDto;
import com.hn.common.exceptions.TemplateException;
import com.hn.user.dto.LoginDto;
import com.hn.user.entity.UserEntity;
import com.hn.user.model.LoginInfoModel;

import java.util.List;

public interface UserService {

    UserEntity getUserById(Long id);

    UserEntity getUserByName(String username);

    UserEntity getUserByToken(String token);

    boolean authUserByToken(String token);

    boolean loginOut(String token, UserDto userDto);

    boolean loginOutAll(UserDto userDto);


    PaginationData<List<UserDto>> getUserPageList(Integer currentPage, Integer pageSize, String sort,
                                                  UserDto userDto) throws IllegalAccessException;

    int hasUserByName(String username);

    int userAdd(UserDto userDto);

    int userUpdate(UserDto userDto) throws TemplateException;

    int userUpdateStatus(UserDto userDto);

    int userDelete(UserDto userDto);

    LoginInfoModel userLogin(LoginDto loginDto);

    String setUserToken(UserEntity user);

}
