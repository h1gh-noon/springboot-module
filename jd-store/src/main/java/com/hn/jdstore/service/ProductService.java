package com.hn.jdstore.service;

import com.hn.jdstore.entity.HanmaProductEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {


    void delete(HanmaProductEntity productEntity);

    Long update(HanmaProductEntity productEntity);

    List<HanmaProductEntity> update(List<HanmaProductEntity> productEntityList);

    HanmaProductEntity findById(Long id);

    List<HanmaProductEntity> getProductListByShopId(Long shopId);

    Page<HanmaProductEntity> getProductPageList(Integer currentPage, Integer pageSize, String name);

}
