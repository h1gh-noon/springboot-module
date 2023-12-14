package com.hn.jdstore.service;

import com.hn.jdstore.domain.entity.ProductDo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {


    void delete(ProductDo productDo);

    Long update(ProductDo productDo);

    List<ProductDo> update(List<ProductDo> productDoList);

    ProductDo findById(Long id);

    List<ProductDo> getProductListByShopId(Long shopId);

    Page<ProductDo> getProductPageList(Integer currentPage, Integer pageSize, String name);

}
