package com.hn.jdstore.controller;

import com.hn.common.api.CommonResponse;
import com.hn.common.api.PaginationData;
import com.hn.common.util.ResponseTool;
import com.hn.common.util.Util;
import com.hn.jdstore.domain.entity.HanmaProductDo;
import com.hn.jdstore.domain.vo.ProductVo;
import com.hn.jdstore.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    public CommonResponse<Long> productAdd(@RequestBody HanmaProductDo hanmaProduct) {
        hanmaProduct.setId(null);
        String t = Util.getTimestampStr();
        hanmaProduct.setCreateTime(t);
        hanmaProduct.setUpdateTime(t);
        return ResponseTool.getSuccessResponse(productService.update(hanmaProduct));
    }

    @RequestMapping("/productDelete")
    @Operation(summary = "删除商品")
    public CommonResponse<Long> productDelete(@RequestParam Long id) {
        HanmaProductDo hanmaProduct = new HanmaProductDo();
        hanmaProduct.setId(id);
        productService.delete(hanmaProduct);
        return ResponseTool.getSuccessResponse();
    }

    @PostMapping("/productUpdate")
    @Operation(summary = "修改商品")
    public CommonResponse<Long> productUpdate(@RequestBody HanmaProductDo hanmaProduct) {
        hanmaProduct.setUpdateTime(null);
        hanmaProduct.setUpdateTime(Util.getTimestampStr());
        return ResponseTool.getSuccessResponse(productService.update(hanmaProduct));
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
    getProductPageList(@RequestParam(required = false,
            defaultValue = "1") @Schema(description = "当前页码") Integer currentPage,
                       @RequestParam(required = false,
                               defaultValue = "20") @Schema(description = "每页条数") Integer pageSize,
                       @RequestBody(required = false) String name) {

        Page<HanmaProductDo> productDoPage = productService.getProductPageList(currentPage,
                pageSize,
                name);
        log.info("productDoPage={}", productDoPage);

        List<ProductVo> list = new ArrayList<>();
        productDoPage.getContent().forEach(h -> {
            ProductVo sm = new ProductVo();
            BeanUtils.copyProperties(h, sm);
            list.add(sm);
        });

        PaginationData<List<ProductVo>> pagination = new PaginationData<>();
        pagination.setCurrentPage(currentPage);
        pagination.setPageSize(pageSize);
        pagination.setTotal(productDoPage.getTotalElements());
        pagination.setData(list);
        return ResponseTool.getSuccessResponse(pagination);
    }

}
