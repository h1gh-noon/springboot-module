package com.hn.jdstore.controller;

import com.alibaba.fastjson2.JSON;
import com.hn.common.api.CommonResponse;
import com.hn.common.api.PaginationData;
import com.hn.common.dto.Validation;
import com.hn.common.util.ResponseTool;
import com.hn.common.util.Util;
import com.hn.jdstore.domain.entity.ProductDo;
import com.hn.jdstore.domain.vo.ProductVo;
import com.hn.jdstore.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public CommonResponse<Long> productAdd(@RequestBody ProductVo productVo) {
        productVo.setId(null);
        ProductDo productDo = new ProductDo();
        BeanUtils.copyProperties(productVo, productDo);
        String t = Util.getTimestampStr();
        productDo.setCreateTime(t);
        productDo.setUpdateTime(t);
        return ResponseTool.getSuccessResponse(productService.update(productDo));
    }

    @RequestMapping("/productDelete")
    @Operation(summary = "删除商品")
    public CommonResponse<Long> productDelete(@RequestParam Long id) {
        ProductDo productDo = new ProductDo();
        productDo.setId(id);
        productService.delete(productDo);
        return ResponseTool.getSuccessResponse();
    }

    @PostMapping("/productUpdate")
    @Operation(summary = "修改商品")
    public CommonResponse<Long> productUpdate(@RequestBody @Validated(Validation.Update.class) ProductVo productVo) {
        ProductDo productDo = new ProductDo();
        BeanUtils.copyProperties(productVo, productDo);
        productDo.setUpdateTime(Util.getTimestampStr());
        return ResponseTool.getSuccessResponse(productService.update(productDo));
    }

    @RequestMapping("/getProductById")
    @Operation(summary = "根据id查询商品")
    public CommonResponse<ProductVo> getProductById(@RequestParam Long id) {

        ProductVo productVo = new ProductVo();
        BeanUtils.copyProperties(productService.findById(id), productVo);
        return ResponseTool.getSuccessResponse(productVo);
    }

    @RequestMapping("/getProductPageList")
    @Operation(summary = "查询商品分页列表")
    public CommonResponse<PaginationData<List<ProductVo>>>
    getProductPageList(@RequestParam(required = false, defaultValue = "1")
                       @Schema(description = "当前页码") Integer currentPage,
                       @RequestParam(required = false, defaultValue = "20")
                       @Schema(description = "每页条数") Integer pageSize) {

        List<Map<String, Object>> pageList = productService.getProductPageList(currentPage,
                pageSize);
        log.info("pageList={}", pageList);
        String json = JSON.toJSONString(Util.underlineToCamelByListMap(pageList));
        System.out.println(json);
        List<ProductVo> productVos = JSON.parseArray(json, ProductVo.class);
        PaginationData<List<ProductVo>> pagination = new PaginationData<>();
        pagination.setCurrentPage(currentPage);
        pagination.setPageSize(pageSize);
        pagination.setTotal(productService.getProductPageTotal());
        pagination.setData(productVos);
        return ResponseTool.getSuccessResponse(pagination);
    }

}
