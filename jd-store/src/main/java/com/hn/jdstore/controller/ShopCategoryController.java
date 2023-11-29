package com.hn.jdstore.controller;

import com.hn.common.api.CommonResponse;
import com.hn.common.dto.Validation;
import com.hn.common.util.ResponseTool;
import com.hn.common.util.Util;
import com.hn.jdstore.entity.HanmaShopCategoryEntity;
import com.hn.jdstore.model.ShopCategoryModel;
import com.hn.jdstore.service.ShopCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Controller
@RequestMapping("/shop")
@Slf4j
@Tag(name = "店铺分类接口")
public class ShopCategoryController {

    @Resource
    private ShopCategoryService shopCategoryService;

    @PostMapping("/shopCategoryAdd")
    @Operation(summary = "添加店铺分类")
    public CommonResponse<Long> shopCategoryAdd(@RequestBody @Validated(Validation.Save.class) HanmaShopCategoryEntity hanmaShopCategory) {
        hanmaShopCategory.setId(null);
        String t = Util.getTimestampStr();
        hanmaShopCategory.setCreateTime(t);
        hanmaShopCategory.setUpdateTime(t);
        return ResponseTool.getSuccessResponse(shopCategoryService.update(hanmaShopCategory));
    }

    @RequestMapping("/shopCategoryDelete")
    @Operation(summary = "删除店铺分类")
    public CommonResponse<Long> shopCategoryDelete(@RequestParam Long id) {

        HanmaShopCategoryEntity hanmaShopCategory = new HanmaShopCategoryEntity();
        hanmaShopCategory.setId(id);
        shopCategoryService.delete(hanmaShopCategory);
        return ResponseTool.getSuccessResponse();
    }

    @PostMapping("/shopCategoryUpdate")
    @Operation(summary = "修改店铺分类")
    public CommonResponse<Long> shopCategoryUpdate(@RequestBody HanmaShopCategoryEntity hanmaShopCategory) {
        hanmaShopCategory.setUpdateTime(null);
        hanmaShopCategory.setUpdateTime(Util.getTimestampStr());
        return ResponseTool.getSuccessResponse(shopCategoryService.update(hanmaShopCategory));
    }

    @RequestMapping("/getShopCategoryById")
    @Operation(summary = "根据id查询店铺分类")
    public CommonResponse<ShopCategoryModel> getShopCategoryById(@RequestParam Long id) {

        ShopCategoryModel shopCategoryModel = new ShopCategoryModel();
        BeanUtils.copyProperties(shopCategoryService.findById(id), shopCategoryModel);
        return ResponseTool.getSuccessResponse(shopCategoryModel);
    }

    @RequestMapping("/getShopCategoryList")
    @Operation(summary = "店铺分类列表")
    public CommonResponse<List<ShopCategoryModel>> getShopCategoryList() {

        List<ShopCategoryModel> list = new ArrayList<>();
        List<HanmaShopCategoryEntity> hanmaShopCategoryList = shopCategoryService.getShopCategoryList();
        log.info("hanmaShopCategoryList={}", hanmaShopCategoryList);
        hanmaShopCategoryList.forEach(h -> {
            ShopCategoryModel shopCategoryModel = new ShopCategoryModel();
            BeanUtils.copyProperties(h, shopCategoryModel);
            list.add(shopCategoryModel);
        });


        return ResponseTool.getSuccessResponse(list);
    }

}
