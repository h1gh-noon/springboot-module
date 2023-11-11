package com.user.userserver.controller;

import com.user.userserver.dto.UserDto;
import com.user.userserver.entity.UserEntity;
import com.user.userserver.exceptions.TemplateException;
import com.user.userserver.model.CommonResponse;
import com.user.userserver.model.PaginationData;
import com.user.userserver.model.UserModel;
import com.user.userserver.service.UserService;
import com.user.userserver.util.ResponseTool;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/getUserById")
    public CommonResponse getUserById(@RequestBody Long id) {

        UserEntity userEntity = userService.getUserById(id);
        UserModel u = new UserModel();
        BeanUtils.copyProperties(userEntity, u);
        return ResponseTool.getSuccessResponse(u);
    }

    /**
     * @param params @RequestParam 分页数据 {currentPage: 1, pageSize: 20}
     * @param data   @RequestBody 筛选条件
     *               {
     *               username: String, // 模糊查询
     *               phone: String, // 模糊查询
     *               status: Integer, // 1启用 0禁用
     *               startTime: String, // 开始时间(根据范围搜索用户创建时间)
     *               endTime: String // 结束时间(根据范围搜索用户创建时间)
     *               }
     * @return CommonResponse
     */
    @PostMapping("/getUserPageList")
    public CommonResponse getUserPageList(@RequestParam(required = false) HashMap<String, String> params,
                                          @RequestBody(required = false) HashMap<String, Object> data) {

        Integer currentPage = Integer.valueOf(params.getOrDefault("currentPage", "1"));
        Integer pageSize = Integer.valueOf(params.getOrDefault("pageSize", "20"));

        data.put("pageSize", pageSize);
        data.put("limitRows", (currentPage - 1) * pageSize);

        PaginationData<List<UserDto>> userPageList = userService.getUserPageList(data);

        PaginationData<List<UserModel>> p = new PaginationData<>();
        p.setTotal(userPageList.getTotal());
        p.setData(userPageList.getData().stream().map(e -> {
            UserModel u = new UserModel();
            BeanUtils.copyProperties(e, u);
            return u;
        }).collect(Collectors.toList()));

        p.setCurrentPage(currentPage);
        p.setPageSize(pageSize);
        return ResponseTool.getSuccessResponse(p);
    }

    /**
     * hasUserByName 查询username用户名是否已存在
     *
     * @param username {
     *                 username: String, 用户名 必须**
     *                 }
     * @return CommonResponse
     */
    @PostMapping("/hasUserByName")
    public CommonResponse hasUserByName(@RequestBody String username) {
        return ResponseTool.getSuccessResponse(userService.hasUserByName(username) > 0);
    }

    /**
     * add
     *
     * @param userDto {
     *                username: String, 用户名 必须**
     *                password: String, 密码 必须** 接收md5大写32位密文**
     *                phone: String, 手机号
     *                permissions: String, 权限
     *                }
     * @return CommonResponse
     */
    @PostMapping("/userAdd")
    public CommonResponse userAdd(@RequestBody @Validated({UserDto.Save.class}) UserDto userDto) {
        int n = userService.userAdd(userDto);
        if (n > 0) {
            return ResponseTool.getSuccessResponse(userDto.getId());
        }
        return ResponseTool.getErrorResponse();
    }

    /**
     * update
     *
     * @param userDto {
     *                id: String,  id必须**
     *                username: String, 用户名
     *                password: String, 密码 接收md5大写32位密文**
     *                phone: String, 手机号
     *                permissions: String, 权限
     *                }
     * @return CommonResponse
     */
    @PostMapping("/userUpdate")
    public CommonResponse userUpdate(@RequestBody @Validated(UserDto.Update.class) UserDto userDto) throws TemplateException {
        userDto.setCreateTime(null);
        int n = userService.userUpdate(userDto);
        if (n > 0) {
            // RedisUtil.
            return ResponseTool.getSuccessResponse();
        }
        return ResponseTool.getErrorResponse();
    }


    /**
     * @param userDto { id: Integer 必须**, status: 1 } 1启用 0禁用
     * @return CommonResponse
     */
    @PostMapping("/userUpdateStatus")
    public CommonResponse userUpdateStatus(@RequestBody @Validated(UserDto.Update.class) UserDto userDto) {
        if (userDto.getStatus() == null) {
            userDto.setStatus(0);
        }
        int n = userService.userUpdateStatus(userDto);
        if (n > 0) {
            return ResponseTool.getSuccessResponse();
        }
        return ResponseTool.getErrorResponse();
    }

    /**
     * @param userDto { id: Integer } || { username: String } id和username二选一
     * @return CommonResponse
     */
    @PostMapping("/userDelete")
    public CommonResponse userDelete(@RequestBody UserDto userDto) {
        if (userDto.getId() != null || userDto.getUsername() != null) {
            int n = userService.userDelete(userDto);
            if (n > 0) {
                return ResponseTool.getSuccessResponse();
            }
        }
        return ResponseTool.getErrorResponse();
    }
}
