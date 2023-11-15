package com.hn.user.service;


import com.hn.user.entity.UserEntity;
import com.hn.user.dto.UserDto;
import com.hn.user.exceptions.TemplateException;
import com.hn.user.model.PaginationData;

import java.util.List;
import java.util.Map;

public interface UserService {

    UserEntity getUserById(Long id);

    UserEntity getUserByName(String username);

    UserEntity getUserByToken(String token);

    boolean authUserByToken(String token);

    boolean loginOut(String token, UserDto userDto);

    boolean loginOutAll(String toke, UserDto userDto);


    PaginationData<List<UserDto>> getUserPageList(Map<String, Object> map);

    int hasUserByName(String username);

    int userAdd(UserDto userDto);

    int userUpdate(UserDto userDto) throws TemplateException;

    int userUpdateStatus(UserDto userDto);

    int userDelete(UserDto userDto);

    String userLogin(UserDto userDto);

}
