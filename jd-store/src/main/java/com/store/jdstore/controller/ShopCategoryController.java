package com.store.jdstore.controller;

import com.store.jdstore.entity.HanmaShopCategoryEntity;
import com.store.jdstore.model.CommonResponse;
import com.store.jdstore.model.ShopCategoryModel;
import com.store.jdstore.service.ShopCategoryService;
import com.store.jdstore.util.ResponseUtil;
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
    public CommonResponse shopCategoryAdd(@RequestBody HanmaShopCategoryEntity hanmaShopCategory) {
        hanmaShopCategory.setId(null);
        return ResponseUtil.getSuccessResponse(shopCategoryService.update(hanmaShopCategory));
    }

    @PostMapping("/shopCategoryDelete")
    public CommonResponse shopCategoryDelete(@RequestBody HanmaShopCategoryEntity hanmaShopCategory) {
        if (hanmaShopCategory.getId() == null) {
            return ResponseUtil.getErrorResponse();
        }
        return ResponseUtil.getSuccessResponse(shopCategoryService.update(hanmaShopCategory));
    }

    @PostMapping("/shopCategoryUpdate")
    public CommonResponse shopCategoryUpdate(@RequestBody HanmaShopCategoryEntity hanmaShopCategory) {
        return ResponseUtil.getSuccessResponse(shopCategoryService.update(hanmaShopCategory));
    }

    @PostMapping("/getShopCategoryById")
    public CommonResponse getShopCategoryById(@RequestBody HanmaShopCategoryEntity hanmaShopCategory) {
        if (hanmaShopCategory.getId() == null) {
            return ResponseUtil.getErrorResponse();
        }

        ShopCategoryModel shopCategoryModel = new ShopCategoryModel();
        BeanUtils.copyProperties(shopCategoryService.findById(hanmaShopCategory.getId()), shopCategoryModel);
        return ResponseUtil.getSuccessResponse(shopCategoryModel);
    }

    @PostMapping("/getShopCategoryList")
    public CommonResponse getShopCategoryList() {

        List<ShopCategoryModel> list = new ArrayList<>();
        List<HanmaShopCategoryEntity> hanmaShopCategoryList = shopCategoryService.getShopCategoryList();
        log.info("hanmaShopCategoryList={}", hanmaShopCategoryList);
        hanmaShopCategoryList.forEach(h -> {
            ShopCategoryModel shopCategoryModel = new ShopCategoryModel();
            BeanUtils.copyProperties(h, shopCategoryModel);
            list.add(shopCategoryModel);
        });


        return ResponseUtil.getSuccessResponse(list);
    }

}
