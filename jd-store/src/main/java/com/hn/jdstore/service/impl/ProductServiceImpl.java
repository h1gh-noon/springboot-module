package com.hn.jdstore.service.impl;

import com.hn.jdstore.entity.HanmaProductEntity;
import com.hn.jdstore.service.ProductService;
import com.hn.jdstore.dao.ProductDao;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductDao productDao;

    @Override
    public void delete(HanmaProductEntity hanmaProduct) {
        productDao.delete(hanmaProduct);
    }

    @Override
    public Long update(HanmaProductEntity hanmaProduct) {
        return productDao.save(hanmaProduct).getId();
    }

    @Override
    public List<HanmaProductEntity> update(List<HanmaProductEntity> productEntityList) {
        return productDao.saveAll(productEntityList);
    }

    @Override
    public HanmaProductEntity findById(Long id) {
        return productDao.findById(id).orElse(null);
    }

    @Override
    public List<HanmaProductEntity> getProductListByShopId(Long shopId) {
        HanmaProductEntity p = new HanmaProductEntity();
        p.setShopId(shopId);
        Example<HanmaProductEntity> example = Example.of(p);
        return productDao.findAll(example);
    }

}
