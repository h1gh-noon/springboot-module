package com.hn.jdstore.controller;

import com.hn.common.api.CommonResponse;
import com.hn.common.api.PaginationData;
import com.hn.common.util.ResponseTool;
import com.hn.common.util.Util;
import com.hn.jdstore.entity.HanmaShopEntity;
import com.hn.jdstore.model.ShopInfoProductModel;
import com.hn.jdstore.model.ShopModel;
import com.hn.jdstore.service.ShopService;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class ShopController {

    @Resource
    private ShopService shopService;

    @PostMapping("/shopAdd")
    public CommonResponse<Long> shopAdd(@RequestBody HanmaShopEntity hanmaShop) {
        hanmaShop.setId(null);
        String time = Util.getTimestampStr();
        hanmaShop.setCreateTime(time);
        hanmaShop.setUpdateTime(time);
        return ResponseTool.getSuccessResponse(shopService.update(hanmaShop));
    }

    @PostMapping("/shopDelete")
    public CommonResponse<Long> shopDelete(@RequestBody HanmaShopEntity hanmaShop) {
        if (hanmaShop.getId() == null) {
            return ResponseTool.getErrorResponse();
        }
        return ResponseTool.getSuccessResponse(shopService.update(hanmaShop));
    }

    @PostMapping("/shopUpdate")
    public CommonResponse<Long> shopUpdate(@RequestBody HanmaShopEntity hanmaShop) {
        hanmaShop.setCreateTime(null);
        hanmaShop.setUpdateTime(Util.getTimestampStr());
        return ResponseTool.getSuccessResponse(shopService.update(hanmaShop));
    }

    @PostMapping("/getShopById")
    public CommonResponse<ShopModel> getShopById(@RequestBody HanmaShopEntity hanmaShop) {
        if (hanmaShop.getId() == null) {
            return ResponseTool.getErrorResponse();
        }

        ShopModel shopModel = new ShopModel();
        BeanUtils.copyProperties(shopService.findById(hanmaShop.getId()), shopModel);
        return ResponseTool.getSuccessResponse(shopModel);
    }

    @PostMapping("/getShopPageList")
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
    public CommonResponse<ShopInfoProductModel> getShopInfoProductList(@PathVariable Long shopId) {

        return ResponseTool.getSuccessResponse(shopService.getShopInfoProductList(shopId));
    }
}
