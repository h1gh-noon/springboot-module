package com.user.userserver.controller;

import com.user.userserver.entity.User;
import com.user.userserver.model.CommonResponse;
import com.user.userserver.model.PaginationData;
import com.user.userserver.model.UserInfo;
import com.user.userserver.service.UserService;
import com.user.userserver.util.ResponseTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/getUserById")
    public CommonResponse getUserById(@RequestBody User user) {

        if (user.getId() == null) {
            return ResponseTool.getErrorResponse();
        }
        return ResponseTool.getSuccessResponse(userService.getUserById(user.getId()));
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
                                          @RequestBody(required = false) HashMap<String, Object> data) throws IllegalAccessException {

        Integer currentPage = Integer.valueOf(params.getOrDefault("currentPage", "1"));
        Integer pageSize = Integer.valueOf(params.getOrDefault("pageSize", "20"));

        data.put("pageSize", pageSize);
        data.put("limitRows", (currentPage - 1) * pageSize);

        PaginationData<List<UserInfo>> p = userService.getUserPageList(data);
        p.setCurrentPage(currentPage);
        p.setPageSize(pageSize);
        return ResponseTool.getSuccessResponse(p);
    }

    /**
     * hasUserByName 查询username用户名是否已存在
     *
     * @param user {
     *             username: String, 用户名 必须**
     *             }
     * @return CommonResponse
     */
    @PostMapping("/hasUserByName")
    public CommonResponse hasUserByName(@RequestBody User user) {
        if (user.getUsername() != null) {
            int n = userService.hasUserByName(user.getUsername());
            return ResponseTool.getSuccessResponse(n > 0);
        }
        return ResponseTool.getErrorResponse();
    }

    /**
     * add
     *
     * @param user {
     *             username: String, 用户名 必须**
     *             password: String, 密码 必须** 接收md5大写32位密文**
     *             phone: String, 手机号
     *             permissions: String, 权限
     *             }
     * @return CommonResponse
     */
    @PostMapping("/userAdd")
    public CommonResponse userAdd(@RequestBody User user) {
        if (user.getUsername() != null && user.getPassword() != null) {
            int n = userService.userAdd(user);
            if (n > 0) {
                return ResponseTool.getSuccessResponse(user.getId());
            }
        }
        return ResponseTool.getErrorResponse();
    }

    /**
     * update
     *
     * @param user {
     *             id: String,  id必须**
     *             username: String, 用户名
     *             password: String, 密码 接收md5大写32位密文**
     *             phone: String, 手机号
     *             permissions: String, 权限
     *             }
     * @return CommonResponse
     */
    @PostMapping("/userUpdate")
    public CommonResponse userUpdate(@RequestBody User user) {
        if (user.getId() != null) {
            int n = userService.userUpdate(user);
            if (n > 0) {
                return ResponseTool.getSuccessResponse(user);
            }
        }
        return ResponseTool.getErrorResponse();
    }


    /**
     * @param user { id: Integer 必须**, status: 1 } 1启用 0禁用
     * @return CommonResponse
     */
    @PostMapping("/userUpdateStatus")
    public CommonResponse userUpdateStatus(@RequestBody User user) {
        if (user.getId() != null) {
            if (user.getStatus() == null) {
                user.setStatus(0);
            }
            int n = userService.userUpdateStatus(user);
            if (n > 0) {
                return ResponseTool.getSuccessResponse();
            }
        }
        return ResponseTool.getErrorResponse();
    }

    /**
     * @param user { id: Integer } || { username: String } id和username二选一
     * @return CommonResponse
     */
    @PostMapping("/userDelete")
    public CommonResponse userDelete(@RequestBody User user) {
        if (user.getId() != null || user.getUsername() != null) {
            int n = userService.userDelete(user);
            if (n > 0) {
                return ResponseTool.getSuccessResponse();
            }
        }
        return ResponseTool.getErrorResponse();
    }
}
