package com.hn.jdstore.controller;

import com.hn.common.api.CommonResponse;
import com.hn.common.util.ResponseTool;
import com.hn.common.util.Util;
import com.hn.jdstore.entity.HanmaProductEntity;
import com.hn.jdstore.model.ProductModel;
import com.hn.jdstore.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Controller
@RequestMapping("/product")
@Slf4j
@Tag(name = "商品接口")
public class ProductController {

    @Resource
    private ProductService productService;

    @PostMapping("/productAdd")
    @Operation(summary = "添加商品")
    public CommonResponse<Long> productAdd(@RequestBody HanmaProductEntity hanmaProduct) {
        hanmaProduct.setId(null);
        String t = Util.getTimestampStr();
        hanmaProduct.setCreateTime(t);
        hanmaProduct.setUpdateTime(t);
        return ResponseTool.getSuccessResponse(productService.update(hanmaProduct));
    }

    @RequestMapping("/productDelete")
    @Operation(summary = "删除商品")
    public CommonResponse<Long> productDelete(@RequestParam Long id) {
        HanmaProductEntity hanmaProduct = new HanmaProductEntity();
        hanmaProduct.setId(id);
        productService.delete(hanmaProduct);
        return ResponseTool.getSuccessResponse();
    }

    @PostMapping("/productUpdate")
    @Operation(summary = "修改商品")
    public CommonResponse<Long> productUpdate(@RequestBody HanmaProductEntity hanmaProduct) {
        hanmaProduct.setUpdateTime(null);
        hanmaProduct.setUpdateTime(Util.getTimestampStr());
        return ResponseTool.getSuccessResponse(productService.update(hanmaProduct));
    }

    @RequestMapping("/getProductById")
    @Operation(summary = "根据id查询商品")
    public CommonResponse<ProductModel> getProductById(@RequestParam Long id) {

        ProductModel productModel = new ProductModel();
        BeanUtils.copyProperties(productService.findById(id), productModel);
        return ResponseTool.getSuccessResponse(productModel);
    }

}
