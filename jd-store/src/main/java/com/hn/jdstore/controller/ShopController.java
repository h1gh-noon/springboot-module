package com.hn.jdstore.controller;

import com.hn.common.api.CommonResponse;
import com.hn.common.api.PaginationData;
import com.hn.common.util.ResponseTool;
import com.hn.common.util.Util;
import com.hn.jdstore.entity.HanmaShopEntity;
import com.hn.jdstore.model.ShopInfoProductModel;
import com.hn.jdstore.model.ShopModel;
import com.hn.jdstore.service.ShopService;
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
@RequestMapping("/shop")
@Slf4j
@Tag(name = "店铺接口")
public class ShopController {

    @Resource
    private ShopService shopService;

    @PostMapping("/shopAdd")
    @Operation(summary = "添加店铺")
    public CommonResponse<Long> shopAdd(@RequestBody HanmaShopEntity hanmaShop) {
        hanmaShop.setId(null);
        String time = Util.getTimestampStr();
        hanmaShop.setCreateTime(time);
        hanmaShop.setUpdateTime(time);
        return ResponseTool.getSuccessResponse(shopService.update(hanmaShop));
    }

    @RequestMapping("/shopDelete")
    @Operation(summary = "删除店铺")
    public CommonResponse<Long> shopDelete(@RequestParam Long id) {
        HanmaShopEntity hanmaShopEntity = new HanmaShopEntity();
        hanmaShopEntity.setId(id);
        shopService.delete(hanmaShopEntity);
        return ResponseTool.getSuccessResponse();
    }

    @PostMapping("/shopUpdate")
    @Operation(summary = "修改店铺")
    public CommonResponse<Long> shopUpdate(@RequestBody HanmaShopEntity hanmaShop) {
        hanmaShop.setCreateTime(null);
        hanmaShop.setUpdateTime(Util.getTimestampStr());
        return ResponseTool.getSuccessResponse(shopService.update(hanmaShop));
    }

    @RequestMapping("/getShopById")
    @Operation(summary = "根据id查询店铺")
    public CommonResponse<ShopModel> getShopById(@RequestParam Long id) {
        ShopModel shopModel = new ShopModel();
        BeanUtils.copyProperties(shopService.findById(id), shopModel);
        return ResponseTool.getSuccessResponse(shopModel);
    }

    @PostMapping("/getShopPageList")
    @Operation(summary = "获取店铺列表分页")
    public CommonResponse<PaginationData<List<ShopModel>>> getShopPageList(
            @RequestParam(required = false, defaultValue = "1") @Schema(description = "当前页码") String currentPage,
            @RequestParam(required = false, defaultValue = "20") @Schema(description = "每页条数") String pageSize,
            @RequestBody(required = false) ShopModel shopModel) {

        Integer cPage = Integer.valueOf(currentPage);
        Integer pSize = Integer.valueOf(pageSize);

        Page<HanmaShopEntity> hanmaShopList = shopService.getShopPageList(cPage,
                pSize,
                shopModel);
        log.info("hanmaShopList={}", hanmaShopList);

        List<ShopModel> list = new ArrayList<>();
        hanmaShopList.getContent().forEach(h -> {
            ShopModel sm = new ShopModel();
            BeanUtils.copyProperties(h, sm);
            list.add(sm);
        });

        PaginationData<List<ShopModel>> pagination = new PaginationData<>();
        pagination.setCurrentPage(cPage);
        pagination.setPageSize(pSize);
        pagination.setTotal(hanmaShopList.getTotalElements());
        pagination.setData(list);
        return ResponseTool.getSuccessResponse(pagination);
    }

    @RequestMapping("/getShopInfoProductList/{shopId}")
    @Operation(summary = "根据店铺id查询商品")
    public CommonResponse<ShopInfoProductModel> getShopInfoProductList(@PathVariable Long shopId) {

        return ResponseTool.getSuccessResponse(shopService.getShopInfoProductList(shopId));
    }
}
