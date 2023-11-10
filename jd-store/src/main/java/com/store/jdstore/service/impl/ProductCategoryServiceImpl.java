package com.store.jdstore.service.impl;

import com.store.jdstore.dao.ProductCategoryDao;
import com.store.jdstore.entity.HanmaProductCategoryEntity;
import com.store.jdstore.service.ProductCategoryService;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Resource
    private ProductCategoryDao productCategoryDao;


    @Override
    public void delete(HanmaProductCategoryEntity hanmaProductCategory) {
        productCategoryDao.delete(hanmaProductCategory);
    }

    @Override
    public Long update(HanmaProductCategoryEntity hanmaProductCategory) {
        return productCategoryDao.save(hanmaProductCategory).getId();
    }

    @Override
    public HanmaProductCategoryEntity findById(Long id) {
        return productCategoryDao.findById(id).orElse(null);
    }

    @Override
    public List<HanmaProductCategoryEntity> findByShopId(Long shopId) {
        HanmaProductCategoryEntity p = new HanmaProductCategoryEntity();
        p.setShopId(shopId);
        Example<HanmaProductCategoryEntity> example = Example.of(p);
        return productCategoryDao.findAll(example);
    }

    @Override
    public List<HanmaProductCategoryEntity> getProductCategoryList() {
        return null;
    }
}
