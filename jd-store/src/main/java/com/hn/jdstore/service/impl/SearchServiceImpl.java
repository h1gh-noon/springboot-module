package com.hn.jdstore.service.impl;

import com.alibaba.fastjson2.JSON;
import com.hn.jdstore.dao.SearchDao;
import com.hn.jdstore.dto.ProductDto;
import com.hn.jdstore.entity.HanmaShopEntity;
import com.hn.jdstore.model.ProductModel;
import com.hn.jdstore.model.ShopModel;
import com.hn.jdstore.service.ProductService;
import com.hn.jdstore.service.SearchService;
import com.hn.jdstore.service.ShopService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SearchServiceImpl implements SearchService {

    @Resource
    private ShopService shopService;

    @Resource
    private ProductService productService;

    @Resource
    private SearchDao searchDao;


    @Override
    public List<ShopModel> searchAll(String content) {

        // Page<HanmaProductEntity> productPageList = productService.getProductPageList(1, 30,
        //         content);
        // Set<Long> ids = new HashSet<>();
        // List<ProductModel> productModels = productPageList.getContent().stream().map(e -> {
        //     ProductModel pm = new ProductModel();
        //     BeanUtils.copyProperties(e, pm);
        //     ids.add(pm.getShopId());
        //     return pm;
        // }).toList();
        //
        // List<ShopModel> shops =
        //         ids.stream().map(e -> {
        //             ShopModel s = new ShopModel();
        //             // mybatis可以优化成where条件查询
        //             BeanUtils.copyProperties(shopService.findById(e), s);
        //             s.setProductList(productModels.stream().filter(p -> p.getShopId().equals(s
        //             .getId())).toList());
        //             return s;
        //         }).collect(Collectors.toList());
        //
        // searchModel.setShops(shops);

        Page<HanmaShopEntity> shopPageList = shopService.searchShopByNamePageList(1, 30, content);
        List<ShopModel> shopModels = shopPageList.getContent().stream().map(e -> {
            ShopModel sm = new ShopModel();
            BeanUtils.copyProperties(e, sm);
            return sm;
        }).toList();
        Set<ShopModel> resultShopModel = new HashSet<>(shopModels);

        List<Map<String, Object>> list = searchDao.searchAll("%" + content + "%");
        List<ProductDto> productDtos = JSON.parseArray(JSON.toJSONString(list), ProductDto.class);
        productDtos.forEach(e -> {
            ShopModel shopModel =
                    resultShopModel.stream().filter(s -> Objects.equals(s.getId(), e.getShopId())).findFirst().orElse(null);
            ProductModel pm = new ProductModel();
            BeanUtils.copyProperties(e, pm);
            if (shopModel == null) {
                ShopModel sm = new ShopModel();
                sm.setId(e.getShopId());
                sm.setName(e.getShopName());
                sm.setState(e.getShopState());
                sm.setDescDetail(e.getShopDescDetail());
                sm.setExpressLimit(e.getShopExpressLimit());
                sm.setExpressPrice(e.getShopExpressPrice());
                sm.setImgUrl(e.getShopImg());
                List<ProductModel> pList = new ArrayList<>();
                pList.add(pm);
                sm.setProductList(pList);
                resultShopModel.add(sm);
            } else if (shopModel.getProductList() == null) {
                List<ProductModel> pList = new ArrayList<>();
                pList.add(pm);
                shopModel.setProductList(pList);
            } else {
                shopModel.getProductList().add(pm);
            }
        });

        return resultShopModel.stream().toList();
    }
}
