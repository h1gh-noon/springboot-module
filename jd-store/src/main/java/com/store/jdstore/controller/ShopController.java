package com.store.jdstore.controller;

import com.store.jdstore.entity.HanmaShopEntity;
import com.store.jdstore.model.CommonResponse;
import com.store.jdstore.model.ShopModel;
import com.store.jdstore.service.ShopService;
import com.store.jdstore.util.ResponseUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public CommonResponse getShopPageList(@RequestBody(required = false) ShopModel shopModel) {

        List<ShopModel> list = new ArrayList<>();
        List<HanmaShopEntity> hanmaShopList = shopService.getShopPageList(shopModel);
        log.info("hanmaShopList={}", hanmaShopList);
        hanmaShopList.forEach(h -> {
            ShopModel sm = new ShopModel();
            BeanUtils.copyProperties(h, sm);
            list.add(sm);
        });


        return ResponseUtil.getSuccessResponse(list);
    }

}
