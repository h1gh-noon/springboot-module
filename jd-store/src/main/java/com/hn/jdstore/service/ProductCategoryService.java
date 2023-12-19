package com.hn.jdstore.service;

import com.hn.jdstore.domain.entity.ProductCategoryDo;
import com.hn.jdstore.domain.vo.ProductCategoryVo;

import java.util.List;

public interface ProductCategoryService {


    void delete(ProductCategoryDo productCategoryDo);

    Long update(ProductCategoryDo productCategoryDo);

    ProductCategoryDo findById(Long id);

    List<ProductCategoryDo> findByShopId(Long shopId);

    List<ProductCategoryVo> getProductCategoryList();

}
