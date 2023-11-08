package com.store.jdstore.service;

import com.store.jdstore.entity.HanmaProductEntity;

import java.util.List;

public interface ProductService {


    void delete(HanmaProductEntity productEntity);

    Long update(HanmaProductEntity productEntity);

    HanmaProductEntity findById(Long id);

    List<HanmaProductEntity> getProductListByShopId(Long shopId);

}
