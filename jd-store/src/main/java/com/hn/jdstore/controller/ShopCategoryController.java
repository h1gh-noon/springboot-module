package com.hn.jdstore.controller;

import com.hn.common.api.CommonResponse;
import com.hn.common.util.ResponseTool;
import com.hn.common.util.Util;
import com.hn.jdstore.entity.HanmaShopCategoryEntity;
import com.hn.jdstore.model.ShopCategoryModel;
import com.hn.jdstore.service.ShopCategoryService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Controller
@RequestMapping("/shop")
@Slf4j
public class ShopCategoryController {

    @Resource
    private ShopCategoryService shopCategoryService;

    @PostMapping("/shopCategoryAdd")
    public CommonResponse<Long> shopCategoryAdd(@RequestBody HanmaShopCategoryEntity hanmaShopCategory) {
        hanmaShopCategory.setId(null);
        String t = Util.getTimestampStr();
        hanmaShopCategory.setCreateTime(t);
        hanmaShopCategory.setUpdateTime(t);
        return ResponseTool.getSuccessResponse(shopCategoryService.update(hanmaShopCategory));
    }

    @PostMapping("/shopCategoryDelete")
    public CommonResponse<Long> shopCategoryDelete(@RequestBody HanmaShopCategoryEntity hanmaShopCategory) {
        if (hanmaShopCategory.getId() == null) {
            return ResponseTool.getErrorResponse();
        }
        return ResponseTool.getSuccessResponse(shopCategoryService.update(hanmaShopCategory));
    }

    @PostMapping("/shopCategoryUpdate")
    public CommonResponse<Long> shopCategoryUpdate(@RequestBody HanmaShopCategoryEntity hanmaShopCategory) {
        hanmaShopCategory.setUpdateTime(null);
        hanmaShopCategory.setUpdateTime(Util.getTimestampStr());
        return ResponseTool.getSuccessResponse(shopCategoryService.update(hanmaShopCategory));
    }

    @PostMapping("/getShopCategoryById")
    public CommonResponse<ShopCategoryModel> getShopCategoryById(@RequestBody HanmaShopCategoryEntity hanmaShopCategory) {
        if (hanmaShopCategory.getId() == null) {
            return ResponseTool.getErrorResponse();
        }

        ShopCategoryModel shopCategoryModel = new ShopCategoryModel();
        BeanUtils.copyProperties(shopCategoryService.findById(hanmaShopCategory.getId()), shopCategoryModel);
        return ResponseTool.getSuccessResponse(shopCategoryModel);
    }

    @PostMapping("/getShopCategoryList")
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
