package com.store.jdstore.service;

import com.store.jdstore.entity.HanmaProductCategoryEntity;

import java.util.List;

public interface ProductCategoryService {


    void delete(HanmaProductCategoryEntity productCategoryEntity);

    Long update(HanmaProductCategoryEntity productCategoryEntity);

    HanmaProductCategoryEntity findById(Long id);

    List<HanmaProductCategoryEntity> findByShopId(Long shopId);

    List<HanmaProductCategoryEntity> getProductCategoryList();

}
