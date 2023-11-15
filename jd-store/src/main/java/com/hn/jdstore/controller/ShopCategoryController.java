package com.hn.jdstore.controller;

import com.hn.jdstore.entity.HanmaShopCategoryEntity;
import com.hn.jdstore.model.CommonResponse;
import com.hn.jdstore.model.ShopCategoryModel;
import com.hn.jdstore.service.ShopCategoryService;
import com.hn.jdstore.util.ResponseUtil;
import com.hn.jdstore.util.Utils;
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
        String t = Utils.getTimestampStr();
        hanmaShopCategory.setCreateTime(t);
        hanmaShopCategory.setUpdateTime(t);
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
        hanmaShopCategory.setUpdateTime(null);
        hanmaShopCategory.setUpdateTime(Utils.getTimestampStr());
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
