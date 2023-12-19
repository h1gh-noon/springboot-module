package com.hn.jdstore.service;

import com.hn.jdstore.domain.entity.ProductDo;

import java.util.List;
import java.util.Map;

public interface ProductService {


    void delete(ProductDo productDo);

    Long update(ProductDo productDo);

    List<ProductDo> update(List<ProductDo> productDoList);

    ProductDo findById(Long id);

    List<ProductDo> getProductListByShopId(Long shopId);

    List<Map<String, Object>> getProductPageList(Integer currentPage, Integer pageSize);

    Long getProductPageTotal();

}
