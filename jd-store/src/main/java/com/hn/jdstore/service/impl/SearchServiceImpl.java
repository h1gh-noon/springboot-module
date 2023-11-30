package com.hn.jdstore.service.impl;

import com.hn.jdstore.entity.HanmaProductEntity;
import com.hn.jdstore.entity.HanmaShopEntity;
import com.hn.jdstore.model.ProductModel;
import com.hn.jdstore.model.SearchModel;
import com.hn.jdstore.model.ShopModel;
import com.hn.jdstore.service.ProductService;
import com.hn.jdstore.service.SearchService;
import com.hn.jdstore.service.ShopService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {

    @Resource
    private ShopService shopService;

    @Resource
    private ProductService productService;


    @Override
    public SearchModel searchAll(String content) {
        SearchModel searchModel = new SearchModel();
        Page<HanmaShopEntity> shopPageList = shopService.searchShopByNamePageList(1, 20, content);
        List<ShopModel> shopModels = shopPageList.getContent().stream().map(e -> {
            ShopModel sm = new ShopModel();
            BeanUtils.copyProperties(e, sm);
            return sm;
        }).collect(Collectors.toList());

        searchModel.setShops(shopModels);
        Page<HanmaProductEntity> productPageList = productService.getProductPageList(1, 20, content);
        List<ProductModel> productModels = productPageList.getContent().stream().map(e -> {
            ProductModel pm = new ProductModel();
            BeanUtils.copyProperties(e, pm);
            return pm;
        }).collect(Collectors.toList());

        searchModel.setProducts(productModels);

        return searchModel;
    }
}
