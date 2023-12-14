package com.hn.jdstore.service;

import com.hn.jdstore.domain.entity.ShopCategoryDo;

import java.util.List;

public interface ShopCategoryService {


    void delete(ShopCategoryDo shopCategoryDo);

    Long update(ShopCategoryDo shopCategoryDo);

    ShopCategoryDo findById(Long id);

    List<ShopCategoryDo> getShopCategoryList();

}
