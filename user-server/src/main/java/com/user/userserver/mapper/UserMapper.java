package com.user.userserver.mapper;

import com.user.userserver.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User getUserById(Long id);

}
