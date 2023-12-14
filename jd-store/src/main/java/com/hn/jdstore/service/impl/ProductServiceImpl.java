package com.hn.jdstore.service.impl;

import com.hn.jdstore.dao.ProductDao;
import com.hn.jdstore.domain.entity.HanmaProductDo;
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
    public void delete(HanmaProductDo hanmaProduct) {
        productDao.delete(hanmaProduct);
    }

    @Override
    public Long update(HanmaProductDo hanmaProduct) {
        return productDao.save(hanmaProduct).getId();
    }

    @Override
    public List<HanmaProductDo> update(List<HanmaProductDo> productDoList) {
        return productDao.saveAll(productDoList);
    }

    @Override
    public HanmaProductDo findById(Long id) {
        return productDao.findById(id).orElse(null);
    }

    public List<HanmaProductDo> getProductListByShopId(Long shopId) {
        HanmaProductDo p = new HanmaProductDo();
        p.setShopId(shopId);
        Example<HanmaProductDo> example = Example.of(p);
        return productDao.findAll(example);
    }

    @Override
    public Page<HanmaProductDo> getProductPageList(Integer currentPage, Integer pageSize,
                                                   String name) {

        Pageable p = PageRequest.of(currentPage - 1, pageSize);
        if (StringUtils.isEmpty(name)) {
            return productDao.findAll(p);
        }
        return productDao.findByNameLike("%" + name + "%", p);
    }
}
