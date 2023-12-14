package com.hn.user.service;


import com.hn.common.api.PaginationData;
import com.hn.common.dto.UserDto;
import com.hn.common.exceptions.TemplateException;
import com.hn.user.domain.request.LoginRequest;
import com.hn.user.domain.entity.UserDo;
import com.hn.user.domain.vo.LoginInfoVo;
import com.hn.user.domain.request.UserListRequest;

import java.util.List;

public interface UserService {

    UserDo getUserById(Long id);

    UserDo getUserByName(String username);

    UserDo getUserByToken(String token);

    boolean authUserByToken(String token);

    boolean loginOut(String token, UserDto userDto);

    boolean loginOutAll(UserDto userDto);


    PaginationData<List<UserDto>> getUserPageList(Integer currentPage, Integer pageSize, String sort,
                                                  UserListRequest userListQuery) throws IllegalAccessException;

    int hasUserByName(String username);

    int userAdd(UserDto userDto);

    int userUpdate(UserDto userDto) throws TemplateException;

    int userUpdateStatus(UserDto userDto);

    int userDelete(UserDto userDto);

    LoginInfoVo userLogin(LoginRequest loginRequest);

    String setUserToken(UserDo user);

}
