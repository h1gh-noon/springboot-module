package com.hn.jdstore.controller;

import com.hn.common.api.CommonResponse;
import com.hn.common.util.ResponseTool;
import com.hn.common.util.Util;
import com.hn.jdstore.entity.HanmaProductCategoryEntity;
import com.hn.jdstore.model.ProductCategoryModel;
import com.hn.jdstore.service.ProductCategoryService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Controller
@RequestMapping("/product")
@Slf4j
public class ProductCategoryController {

    @Resource
    private ProductCategoryService productCategoryService;

    @PostMapping("/productCategoryAdd")
    public CommonResponse productCategoryAdd(@RequestBody HanmaProductCategoryEntity hanmaProductCategory) {
        hanmaProductCategory.setId(null);
        String t = Util.getTimestampStr();
        hanmaProductCategory.setCreateTime(t);
        hanmaProductCategory.setUpdateTime(t);
        return ResponseTool.getSuccessResponse(productCategoryService.update(hanmaProductCategory));
    }

    @PostMapping("/productCategoryDelete")
    public CommonResponse productCategoryDelete(@RequestBody HanmaProductCategoryEntity hanmaProductCategory) {
        if (hanmaProductCategory.getId() == null) {
            return ResponseTool.getErrorResponse();
        }
        return ResponseTool.getSuccessResponse(productCategoryService.update(hanmaProductCategory));
    }

    @PostMapping("/productCategoryUpdate")
    public CommonResponse productCategoryUpdate(@RequestBody HanmaProductCategoryEntity hanmaProductCategory) {
        hanmaProductCategory.setUpdateTime(null);
        hanmaProductCategory.setUpdateTime(Util.getTimestampStr());
        return ResponseTool.getSuccessResponse(productCategoryService.update(hanmaProductCategory));
    }

    @PostMapping("/getProductCategoryById")
    public CommonResponse getProductCategoryById(@RequestBody HanmaProductCategoryEntity hanmaProductCategory) {
        if (hanmaProductCategory.getId() == null) {
            return ResponseTool.getErrorResponse();
        }

        ProductCategoryModel productCategoryModel = new ProductCategoryModel();
        BeanUtils.copyProperties(productCategoryService.findById(hanmaProductCategory.getId()), productCategoryModel);
        return ResponseTool.getSuccessResponse(productCategoryModel);
    }

    @PostMapping("/getProductCategoryList")
    public CommonResponse getProductCategoryList() {

        List<HanmaProductCategoryEntity> hanmaProductCategoryList = productCategoryService.getProductCategoryList();
        log.info("hanmaProductCategoryList={}", hanmaProductCategoryList);
        List<ProductCategoryModel> list = hanmaProductCategoryList.stream().map(h -> {
            ProductCategoryModel productCategoryModel = new ProductCategoryModel();
            BeanUtils.copyProperties(h, productCategoryModel);
            return productCategoryModel;
        }).collect(Collectors.toList());

        return ResponseTool.getSuccessResponse(list);
    }

}
