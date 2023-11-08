package com.store.jdstore.service.impl;

import com.store.jdstore.dao.ProductCategoryDao;
import com.store.jdstore.entity.HanmaProductCategoryEntity;
import com.store.jdstore.service.ProductCategoryService;
import lombok.Data;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class ProductCategoryServiceImpl implements ProductCategoryService {


    private ProductCategoryDao shopCategoryDao;

    public ProductCategoryServiceImpl(ProductCategoryDao shopCategoryDao) {
        this.shopCategoryDao = shopCategoryDao;
    }

    @Override
    public void delete(HanmaProductCategoryEntity hanmaProductCategory) {
        shopCategoryDao.delete(hanmaProductCategory);
    }

    @Override
    public Long update(HanmaProductCategoryEntity hanmaProductCategory) {
        return shopCategoryDao.save(hanmaProductCategory).getId();
    }

    @Override
    public HanmaProductCategoryEntity findById(Long id) {
        return shopCategoryDao.findById(id).orElse(null);
    }

    @Override
    public List<HanmaProductCategoryEntity> findByShopId(Long shopId) {
        HanmaProductCategoryEntity p = new HanmaProductCategoryEntity();
        p.setShopId(shopId);
        Example<HanmaProductCategoryEntity> example = Example.of(p);
        return shopCategoryDao.findAll(example);
    }

    @Override
    public List<HanmaProductCategoryEntity> getProductCategoryList() {
        return null;
    }
}
