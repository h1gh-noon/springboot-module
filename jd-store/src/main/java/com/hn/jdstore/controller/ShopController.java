package com.hn.jdstore.controller;

import com.hn.common.api.CommonResponse;
import com.hn.common.api.PaginationData;
import com.hn.common.dto.Validation;
import com.hn.common.util.ResponseTool;
import com.hn.common.util.Util;
import com.hn.jdstore.domain.entity.ShopDo;
import com.hn.jdstore.domain.vo.ShopInfoProductVo;
import com.hn.jdstore.domain.vo.ShopVo;
import com.hn.jdstore.service.ShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Controller
@RequestMapping("/shop")
@Slf4j
@Tag(name = "店铺接口")
public class ShopController {

    @Resource
    private ShopService shopService;

    @PostMapping("/shopAdd")
    @Operation(summary = "添加店铺")
    public CommonResponse<Long> shopAdd(@RequestBody ShopVo shopVo) {

        shopVo.setId(null);
        ShopDo shopDo = new ShopDo();
        BeanUtils.copyProperties(shopVo, shopDo);
        String time = Util.getTimestampStr();
        shopDo.setCreateTime(time);
        shopDo.setUpdateTime(time);
        return ResponseTool.getSuccessResponse(shopService.update(shopDo));
    }

    @RequestMapping("/shopDelete")
    @Operation(summary = "删除店铺")
    public CommonResponse<Long> shopDelete(@RequestParam Long id) {
        ShopDo shopDo = new ShopDo();
        shopDo.setId(id);
        shopService.delete(shopDo);
        return ResponseTool.getSuccessResponse();
    }

    @PostMapping("/shopUpdate")
    @Operation(summary = "修改店铺")
    public CommonResponse<Long> shopUpdate(@RequestBody @Validated(Validation.Update.class) ShopVo shopVo) {
        ShopDo shopDo = new ShopDo();
        BeanUtils.copyProperties(shopVo, shopDo);
        shopDo.setCreateTime(null);
        shopDo.setUpdateTime(Util.getTimestampStr());
        return ResponseTool.getSuccessResponse(shopService.update(shopDo));
    }

    @RequestMapping("/getShopById")
    @Operation(summary = "根据id查询店铺")
    public CommonResponse<ShopVo> getShopById(@RequestParam Long id) {
        ShopVo shopVo = new ShopVo();
        BeanUtils.copyProperties(shopService.findById(id), shopVo);
        return ResponseTool.getSuccessResponse(shopVo);
    }

    @PostMapping("/getShopPageList")
    @Operation(summary = "获取店铺列表分页")
    public CommonResponse<PaginationData<List<ShopVo>>> getShopPageList(
            @RequestParam(required = false, defaultValue = "1") @Schema(description = "当前页码") String currentPage,
            @RequestParam(required = false, defaultValue = "20") @Schema(description = "每页条数") String pageSize,
            @RequestBody(required = false) ShopVo shopVo) {

        Integer cPage = Integer.valueOf(currentPage);
        Integer pSize = Integer.valueOf(pageSize);

        Page<ShopDo> shopDoList = shopService.getShopPageList(cPage,
                pSize,
                shopVo);
        log.info("shopDoList={}", shopDoList);

        List<ShopVo> list = new ArrayList<>();
        shopDoList.getContent().forEach(h -> {
            ShopVo sm = new ShopVo();
            BeanUtils.copyProperties(h, sm);
            list.add(sm);
        });

        PaginationData<List<ShopVo>> pagination = new PaginationData<>();
        pagination.setCurrentPage(cPage);
        pagination.setPageSize(pSize);
        pagination.setTotal(shopDoList.getTotalElements());
        pagination.setData(list);
        return ResponseTool.getSuccessResponse(pagination);
    }

    @RequestMapping("/getShopInfoProductList/{shopId}")
    @Operation(summary = "根据店铺id查询商品")
    public CommonResponse<ShopInfoProductVo> getShopInfoProductList(@PathVariable Long shopId) {

        return ResponseTool.getSuccessResponse(shopService.getShopInfoProductList(shopId));
    }
}
