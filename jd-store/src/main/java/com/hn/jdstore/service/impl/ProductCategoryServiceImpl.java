package com.hn.jdstore.service.impl;

import com.hn.jdstore.dao.ProductCategoryDao;
import com.hn.jdstore.domain.entity.HanmaProductCategoryDo;
import com.hn.jdstore.service.ProductCategoryService;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Resource
    private ProductCategoryDao productCategoryDao;


    @Override
    public void delete(HanmaProductCategoryDo hanmaProductCategory) {
        productCategoryDao.delete(hanmaProductCategory);
    }

    @Override
    public Long update(HanmaProductCategoryDo hanmaProductCategory) {
        return productCategoryDao.save(hanmaProductCategory).getId();
    }

    @Override
    public HanmaProductCategoryDo findById(Long id) {
        return productCategoryDao.findById(id).orElse(null);
    }

    @Override
    public List<HanmaProductCategoryDo> findByShopId(Long shopId) {
        HanmaProductCategoryDo p = new HanmaProductCategoryDo();
        p.setShopId(shopId);
        Example<HanmaProductCategoryDo> example = Example.of(p);
        return productCategoryDao.findAll(example);
    }

    @Override
    public List<HanmaProductCategoryDo> getProductCategoryList() {
        return productCategoryDao.findAll();
    }
}
