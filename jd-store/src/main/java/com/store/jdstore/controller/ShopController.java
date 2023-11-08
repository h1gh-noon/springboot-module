package com.store.jdstore.controller;

import com.store.jdstore.entity.HanmaShopEntity;
import com.store.jdstore.model.CommonResponse;
import com.store.jdstore.model.Pagination;
import com.store.jdstore.model.ShopModel;
import com.store.jdstore.service.ShopService;
import com.store.jdstore.util.ResponseUtil;
import com.store.jdstore.util.Utils;
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
    public CommonResponse shopAdd(@RequestBody HanmaShopEntity hanmaShop) {
        hanmaShop.setId(null);
        String time = Utils.getTimestampStr();
        hanmaShop.setCreateTime(time);
        hanmaShop.setUpdateTime(time);
        return ResponseUtil.getSuccessResponse(shopService.update(hanmaShop));
    }

    @PostMapping("/shopDelete")
    public CommonResponse shopDelete(@RequestBody HanmaShopEntity hanmaShop) {
        if (hanmaShop.getId() == null) {
            return ResponseUtil.getErrorResponse();
        }
        return ResponseUtil.getSuccessResponse(shopService.update(hanmaShop));
    }

    @PostMapping("/shopUpdate")
    public CommonResponse shopUpdate(@RequestBody HanmaShopEntity hanmaShop) {
        hanmaShop.setCreateTime(null);
        hanmaShop.setUpdateTime(Utils.getTimestampStr());
        return ResponseUtil.getSuccessResponse(shopService.update(hanmaShop));
    }

    @PostMapping("/getShopById")
    public CommonResponse getShopById(@RequestBody HanmaShopEntity hanmaShop) {
        if (hanmaShop.getId() == null) {
            return ResponseUtil.getErrorResponse();
        }

        ShopModel shopModel = new ShopModel();
        BeanUtils.copyProperties(shopService.findById(hanmaShop.getId()), shopModel);
        return ResponseUtil.getSuccessResponse(shopModel);
    }

    @PostMapping("/getShopPageList")
    public CommonResponse getShopPageList(@RequestParam(required = false) Pagination<ShopModel> pagination,
                                          @RequestBody(required = false) ShopModel shopModel) {

        if (pagination == null || pagination.getPageSize() == null || pagination.getCurrentPage() == null) {
            pagination = new Pagination<>();
        }
        Page<HanmaShopEntity> hanmaShopList = shopService.getShopPageList(pagination, shopModel);
        log.info("hanmaShopList={}", hanmaShopList);

        List<ShopModel> list = new ArrayList<>();
        hanmaShopList.getContent().forEach(h -> {
            ShopModel sm = new ShopModel();
            BeanUtils.copyProperties(h, sm);
            list.add(sm);
        });

        pagination.setTotal(hanmaShopList.getTotalElements());
        pagination.setList(list);
        return ResponseUtil.getSuccessResponse(pagination);
    }

    @RequestMapping("/getShopInfoProductList/{shopId}")
    public CommonResponse getShopInfoProductList(@PathVariable Long shopId) {

        return ResponseUtil.getSuccessResponse(shopService.getShopInfoProductList(shopId));
    }
}
