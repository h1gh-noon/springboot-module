package com.hn.jdstore.service;

import com.hn.jdstore.domain.entity.HanmaProductCategoryDo;

import java.util.List;

public interface ProductCategoryService {


    void delete(HanmaProductCategoryDo productCategoryDo);

    Long update(HanmaProductCategoryDo productCategoryDo);

    HanmaProductCategoryDo findById(Long id);

    List<HanmaProductCategoryDo> findByShopId(Long shopId);

    List<HanmaProductCategoryDo> getProductCategoryList();

}
