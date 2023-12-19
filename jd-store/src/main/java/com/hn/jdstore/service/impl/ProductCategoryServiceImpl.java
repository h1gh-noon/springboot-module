package com.hn.jdstore.service.impl;

import com.alibaba.fastjson2.JSON;
import com.hn.common.util.Util;
import com.hn.jdstore.dao.ProductCategoryDao;
import com.hn.jdstore.domain.entity.ProductCategoryDo;
import com.hn.jdstore.domain.vo.ProductCategoryVo;
import com.hn.jdstore.service.ProductCategoryService;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Resource
    private ProductCategoryDao productCategoryDao;


    @Override
    public void delete(ProductCategoryDo productCategoryDo) {
        productCategoryDao.delete(productCategoryDo);
    }

    @Override
    public Long update(ProductCategoryDo productCategoryDo) {
        return productCategoryDao.save(productCategoryDo).getId();
    }

    @Override
    public ProductCategoryDo findById(Long id) {
        return productCategoryDao.findById(id).orElse(null);
    }

    @Override
    public List<ProductCategoryDo> findByShopId(Long shopId) {
        ProductCategoryDo p = new ProductCategoryDo();
        p.setShopId(shopId);
        Example<ProductCategoryDo> example = Example.of(p);
        return productCategoryDao.findAll(example);
    }

    @Override
    public List<ProductCategoryVo> getProductCategoryList() {
        List<Map<String, Object>> productCategoryList = productCategoryDao.getProductCategoryList();
        String json = JSON.toJSONString(Util.underlineToCamelByListMap(productCategoryList));
        return JSON.parseArray(json, ProductCategoryVo.class);
    }
}
