package com.hn.jdstore.service.impl;

import com.hn.jdstore.dao.ProductDao;
import com.hn.jdstore.entity.HanmaProductEntity;
import com.hn.jdstore.service.ProductService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public List<HanmaProductEntity> getProductListByShopId(Long shopId) {
        HanmaProductEntity p = new HanmaProductEntity();
        p.setShopId(shopId);
        Example<HanmaProductEntity> example = Example.of(p);
        return productDao.findAll(example);
    }

    @Override
    public Page<HanmaProductEntity> getProductPageList(Integer currentPage, Integer pageSize,
                                                       String name) {

        Pageable p = PageRequest.of(currentPage - 1, pageSize);
        if (StringUtils.isEmpty(name)) {
            return productDao.findAll(p);
        }
        return productDao.findByNameLike("%" + name + "%", p);
    }
}
