package com.hn.user.controller;

import com.hn.common.api.CommonResponse;
import com.hn.common.api.PaginationData;
import com.hn.common.constant.SortConstant;
import com.hn.common.dto.UserDto;
import com.hn.common.dto.Validation;
import com.hn.common.exceptions.TemplateException;
import com.hn.common.util.ResponseTool;
import com.hn.user.entity.UserEntity;
import com.hn.user.model.UserModel;
import com.hn.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@Tag(name = "User", description = "用户相关")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/getUserById")
    @Operation(summary = "查询-根据id查询用户", method = "post", description = "根据id查询用户")
    public CommonResponse<UserModel> getUserById(@RequestParam String id) {

        UserEntity userEntity = userService.getUserById(Long.parseLong(id));
        UserModel u = new UserModel();
        BeanUtils.copyProperties(userEntity, u);
        return ResponseTool.getSuccessResponse(u);
    }

    /**
     * @param currentPage: 1, pageSize: 20
     * @param userDto      @RequestBody 筛选条件
     *                     {
     *                     username: String, // 模糊查询
     *                     phone: String, // 模糊查询
     *                     status: Integer, // 1启用 0禁用
     *                     startTime: String, // 开始时间(根据范围搜索用户创建时间)
     *                     endTime: String // 结束时间(根据范围搜索用户创建时间)
     *                     }
     * @return CommonResponse
     */
    @PostMapping("/getUserPageList")
    @Operation(summary = "查询-用户列表分页", method = "post", description = "用户列表分页-条件查询 条件放到body中")
    public CommonResponse<PaginationData<List<UserModel>>> getUserPageList(
            @RequestParam(required = false, defaultValue = "1") @Schema(description = "当前页码") String currentPage,
            @RequestParam(required = false, defaultValue = "20") @Schema(description = "每页条数") String pageSize,
            @RequestParam(required = false) @Schema(title = "排序", description = "排序 默认顺序不传 降序: `字段名-sort_down` 升序: " +
                    "`字段名-sort_up`") String sort,
            @RequestBody(required = false) UserDto userDto) throws IllegalAccessException {


        Integer cPage = Integer.valueOf(currentPage);
        Integer pSize = Integer.valueOf(pageSize);

        PaginationData<List<UserDto>> userPageList = userService.getUserPageList(cPage, pSize,
                SortConstant.sortFieldValid(sort), userDto);

        PaginationData<List<UserModel>> p = new PaginationData<>();
        p.setTotal(userPageList.getTotal());
        p.setData(userPageList.getData().stream().map(e -> {
            UserModel u = new UserModel();
            BeanUtils.copyProperties(e, u);
            return u;
        }).collect(Collectors.toList()));

        p.setCurrentPage(cPage);
        p.setPageSize(pSize);
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
    @RequestMapping("/hasUserByName")
    @Operation(summary = "查询-根据username查询用户", method = "post", description = "查询username用户名是否已存在")
    public CommonResponse<Boolean> hasUserByName(@RequestParam String username) {
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
    @Operation(summary = "新增-用户", method = "post", description = "新增用户")
    public CommonResponse<Long> userAdd(@RequestBody @Validated({Validation.Save.class}) UserDto userDto) {
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
    @Operation(summary = "修改-修改用户", method = "post", description = "修改用户")
    public CommonResponse userUpdate(@RequestBody @Validated(Validation.Update.class) UserDto userDto) throws TemplateException {
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
    @Operation(summary = "修改-修改用户状态 启用禁用", method = "post", description = "修改用户 启用禁用")
    public CommonResponse userUpdateStatus(@RequestBody @Validated(Validation.Update.class) UserDto userDto) {
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
     * @param id || username
     * @return CommonResponse
     */
    @PostMapping("/userDelete")
    @Operation(summary = "删除-用户", method = "post", description = "删除用户")
    public CommonResponse userDelete(@RequestParam(required = false) @NotBlank String id,
                                     @RequestParam(required = false) @NotBlank String username) {
        if (id != null || username != null) {
            UserDto userDto = new UserDto();
            if (id != null) {
                userDto.setId(Long.parseLong(id));
            } else {
                userDto.setUsername(username);
            }
            int n = userService.userDelete(userDto);
            if (n > 0) {
                return ResponseTool.getSuccessResponse();
            }
        }
        return ResponseTool.getErrorResponse();
    }
}
