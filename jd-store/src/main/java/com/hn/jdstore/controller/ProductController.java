package com.hn.jdstore.controller;

import com.hn.jdstore.entity.HanmaProductEntity;
import com.hn.jdstore.model.CommonResponse;
import com.hn.jdstore.model.ProductModel;
import com.hn.jdstore.service.ProductService;
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

@RestController
@Controller
@RequestMapping("/product")
@Slf4j
public class ProductController {

    @Resource
    private ProductService productService;

    @PostMapping("/productAdd")
    public CommonResponse productAdd(@RequestBody HanmaProductEntity hanmaProduct) {
        hanmaProduct.setId(null);
        String t = Utils.getTimestampStr();
        hanmaProduct.setCreateTime(t);
        hanmaProduct.setUpdateTime(t);
        return ResponseUtil.getSuccessResponse(productService.update(hanmaProduct));
    }

    @PostMapping("/productDelete")
    public CommonResponse productDelete(@RequestBody HanmaProductEntity hanmaProduct) {
        if (hanmaProduct.getId() == null) {
            return ResponseUtil.getErrorResponse();
        }
        return ResponseUtil.getSuccessResponse(productService.update(hanmaProduct));
    }

    @PostMapping("/productUpdate")
    public CommonResponse productUpdate(@RequestBody HanmaProductEntity hanmaProduct) {
        hanmaProduct.setUpdateTime(null);
        hanmaProduct.setUpdateTime(Utils.getTimestampStr());
        return ResponseUtil.getSuccessResponse(productService.update(hanmaProduct));
    }

    @PostMapping("/getProductById")
    public CommonResponse getProductById(@RequestBody HanmaProductEntity hanmaProduct) {
        if (hanmaProduct.getId() == null) {
            return ResponseUtil.getErrorResponse();
        }

        ProductModel productModel = new ProductModel();
        BeanUtils.copyProperties(productService.findById(hanmaProduct.getId()), productModel);
        return ResponseUtil.getSuccessResponse(productModel);
    }

}
