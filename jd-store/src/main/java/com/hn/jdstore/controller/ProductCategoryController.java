package com.hn.jdstore.controller;

import com.hn.common.api.CommonResponse;
import com.hn.common.util.ResponseTool;
import com.hn.common.util.Util;
import com.hn.jdstore.domain.entity.HanmaProductCategoryDo;
import com.hn.jdstore.domain.vo.ProductCategoryVo;
import com.hn.jdstore.service.ProductCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Controller
@RequestMapping("/product")
@Slf4j
@Tag(name = "商品分类接口")
public class ProductCategoryController {

    @Resource
    private ProductCategoryService productCategoryService;

    @PostMapping("/productCategoryAdd")
    @Operation(summary = "添加商品分类")
    public CommonResponse<Long> productCategoryAdd(@RequestBody HanmaProductCategoryDo hanmaProductCategory) {
        hanmaProductCategory.setId(null);
        String t = Util.getTimestampStr();
        hanmaProductCategory.setCreateTime(t);
        hanmaProductCategory.setUpdateTime(t);
        return ResponseTool.getSuccessResponse(productCategoryService.update(hanmaProductCategory));
    }

    @RequestMapping("/productCategoryDelete")
    @Operation(summary = "删除商品分类")
    public CommonResponse<Long> productCategoryDelete(@RequestParam Long id) {

        HanmaProductCategoryDo hanmaProductCategory = new HanmaProductCategoryDo();
        hanmaProductCategory.setId(id);
        productCategoryService.delete(hanmaProductCategory);
        return ResponseTool.getSuccessResponse();
    }

    @PostMapping("/productCategoryUpdate")
    @Operation(summary = "修改商品分类")
    public CommonResponse<Long> productCategoryUpdate(@RequestBody HanmaProductCategoryDo hanmaProductCategory) {
        hanmaProductCategory.setUpdateTime(null);
        hanmaProductCategory.setUpdateTime(Util.getTimestampStr());
        return ResponseTool.getSuccessResponse(productCategoryService.update(hanmaProductCategory));
    }

    @RequestMapping("/getProductCategoryById")
    @Operation(summary = "根据id查询商品分类")
    public CommonResponse<ProductCategoryVo> getProductCategoryById(@RequestParam Long id) {

        ProductCategoryVo productCategoryVo = new ProductCategoryVo();
        BeanUtils.copyProperties(productCategoryService.findById(id), productCategoryVo);
        return ResponseTool.getSuccessResponse(productCategoryVo);
    }

    @RequestMapping("/getProductCategoryList")
    @Operation(summary = "查询商品分类列表")
    public CommonResponse<List<ProductCategoryVo>> getProductCategoryList() {

        List<HanmaProductCategoryDo> hanmaProductCategoryList = productCategoryService.getProductCategoryList();
        log.info("hanmaProductCategoryList={}", hanmaProductCategoryList);
        List<ProductCategoryVo> list = new ArrayList<>();
        if (hanmaProductCategoryList != null) {
            list = hanmaProductCategoryList.stream().map(h -> {
                ProductCategoryVo productCategoryVo = new ProductCategoryVo();
                BeanUtils.copyProperties(h, productCategoryVo);
                return productCategoryVo;
            }).collect(Collectors.toList());
        }

        return ResponseTool.getSuccessResponse(list);
    }

}
