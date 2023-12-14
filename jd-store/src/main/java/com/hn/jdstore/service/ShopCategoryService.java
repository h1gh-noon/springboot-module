package com.hn.jdstore.service;

import com.hn.jdstore.domain.entity.HanmaShopCategoryDo;

import java.util.List;

public interface ShopCategoryService {


    void delete(HanmaShopCategoryDo hanmaShopCategory);

    Long update(HanmaShopCategoryDo hanmaShopCategory);

    HanmaShopCategoryDo findById(Long id);

    List<HanmaShopCategoryDo> getShopCategoryList();

}
