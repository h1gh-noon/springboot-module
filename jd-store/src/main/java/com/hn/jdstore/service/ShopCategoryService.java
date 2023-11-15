package com.hn.jdstore.service;

import com.hn.jdstore.entity.HanmaShopCategoryEntity;

import java.util.List;

public interface ShopCategoryService {


    void delete(HanmaShopCategoryEntity hanmaShopCategory);

    Long update(HanmaShopCategoryEntity hanmaShopCategory);

    HanmaShopCategoryEntity findById(Long id);

    List<HanmaShopCategoryEntity> getShopCategoryList();

}
