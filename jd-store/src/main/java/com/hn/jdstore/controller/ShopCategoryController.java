package com.hn.jdstore.controller;

import com.hn.common.api.CommonResponse;
import com.hn.common.dto.Validation;
import com.hn.common.util.ResponseTool;
import com.hn.common.util.Util;
import com.hn.jdstore.domain.entity.HanmaShopCategoryDo;
import com.hn.jdstore.domain.vo.ShopCategoryVo;
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
    public CommonResponse<Long> shopCategoryAdd(@RequestBody @Validated(Validation.Save.class) ShopCategoryVo shopCategoryVo) {
        shopCategoryVo.setId(null);
        HanmaShopCategoryDo shopCategoryDo = new HanmaShopCategoryDo();
        BeanUtils.copyProperties(shopCategoryVo, shopCategoryDo);
        String t = Util.getTimestampStr();
        shopCategoryDo.setCreateTime(t);
        shopCategoryDo.setUpdateTime(t);
        return ResponseTool.getSuccessResponse(shopCategoryService.update(shopCategoryDo));
    }

    @RequestMapping("/shopCategoryDelete")
    @Operation(summary = "删除店铺分类")
    public CommonResponse<Long> shopCategoryDelete(@RequestParam Long id) {

        HanmaShopCategoryDo hanmaShopCategory = new HanmaShopCategoryDo();
        hanmaShopCategory.setId(id);
        shopCategoryService.delete(hanmaShopCategory);
        return ResponseTool.getSuccessResponse();
    }

    @PostMapping("/shopCategoryUpdate")
    @Operation(summary = "修改店铺分类")
    public CommonResponse<Long> shopCategoryUpdate(@RequestBody @Validated(Validation.Update.class) ShopCategoryVo shopCategoryVo) {
        HanmaShopCategoryDo shopCategoryDo = new HanmaShopCategoryDo();
        BeanUtils.copyProperties(shopCategoryVo, shopCategoryDo);
        shopCategoryDo.setUpdateTime(Util.getTimestampStr());
        return ResponseTool.getSuccessResponse(shopCategoryService.update(shopCategoryDo));
    }

    @RequestMapping("/getShopCategoryById")
    @Operation(summary = "根据id查询店铺分类")
    public CommonResponse<ShopCategoryVo> getShopCategoryById(@RequestParam Long id) {

        ShopCategoryVo shopCategoryVo = new ShopCategoryVo();
        BeanUtils.copyProperties(shopCategoryService.findById(id), shopCategoryVo);
        return ResponseTool.getSuccessResponse(shopCategoryVo);
    }

    @RequestMapping("/getShopCategoryList")
    @Operation(summary = "店铺分类列表")
    public CommonResponse<List<ShopCategoryVo>> getShopCategoryList() {

        List<ShopCategoryVo> list = new ArrayList<>();
        List<HanmaShopCategoryDo> hanmaShopCategoryList = shopCategoryService.getShopCategoryList();
        log.info("hanmaShopCategoryList={}", hanmaShopCategoryList);
        hanmaShopCategoryList.forEach(h -> {
            ShopCategoryVo shopCategoryVo = new ShopCategoryVo();
            BeanUtils.copyProperties(h, shopCategoryVo);
            list.add(shopCategoryVo);
        });


        return ResponseTool.getSuccessResponse(list);
    }

}
