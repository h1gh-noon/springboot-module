package com.hn.jdstore.service;

import com.hn.jdstore.domain.entity.HanmaProductDo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {


    void delete(HanmaProductDo productDo);

    Long update(HanmaProductDo productDo);

    List<HanmaProductDo> update(List<HanmaProductDo> productDoList);

    HanmaProductDo findById(Long id);

    List<HanmaProductDo> getProductListByShopId(Long shopId);

    Page<HanmaProductDo> getProductPageList(Integer currentPage, Integer pageSize, String name);

}
