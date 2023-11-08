package com.store.jdstore.service.impl;

import com.store.jdstore.dao.ProductDao;
import com.store.jdstore.entity.HanmaProductEntity;
import com.store.jdstore.service.ProductService;
import lombok.Data;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class ProductServiceImpl implements ProductService {


    private ProductDao shopCategoryDao;

    public ProductServiceImpl(ProductDao shopCategoryDao) {
        this.shopCategoryDao = shopCategoryDao;
    }

    @Override
    public void delete(HanmaProductEntity hanmaProduct) {
        shopCategoryDao.delete(hanmaProduct);
    }

    @Override
    public Long update(HanmaProductEntity hanmaProduct) {
        return shopCategoryDao.save(hanmaProduct).getId();
    }

    @Override
    public HanmaProductEntity findById(Long id) {
        return shopCategoryDao.findById(id).orElse(null);
    }

    @Override
    public List<HanmaProductEntity> getProductListByShopId(Long shopId) {
        HanmaProductEntity p = new HanmaProductEntity();
        p.setShopId(shopId);
        Example<HanmaProductEntity> example = Example.of(p);
        return shopCategoryDao.findAll(example);
    }

}
