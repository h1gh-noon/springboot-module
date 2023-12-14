package com.hn.jdstore.service.impl;

import com.hn.jdstore.dao.ProductDao;
import com.hn.jdstore.domain.entity.ProductDo;
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
    public void delete(ProductDo productDo) {
        productDao.delete(productDo);
    }

    @Override
    public Long update(ProductDo productDo) {
        return productDao.save(productDo).getId();
    }

    @Override
    public List<ProductDo> update(List<ProductDo> productDoList) {
        return productDao.saveAll(productDoList);
    }

    @Override
    public ProductDo findById(Long id) {
        return productDao.findById(id).orElse(null);
    }

    public List<ProductDo> getProductListByShopId(Long shopId) {
        ProductDo p = new ProductDo();
        p.setShopId(shopId);
        Example<ProductDo> example = Example.of(p);
        return productDao.findAll(example);
    }

    @Override
    public Page<ProductDo> getProductPageList(Integer currentPage, Integer pageSize,
                                              String name) {

        Pageable p = PageRequest.of(currentPage - 1, pageSize);
        if (StringUtils.isEmpty(name)) {
            return productDao.findAll(p);
        }
        return productDao.findByNameLike("%" + name + "%", p);
    }
}
