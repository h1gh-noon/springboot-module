package com.hn.jdstore.service.impl;

import com.hn.jdstore.dao.ProductDao;
import com.hn.jdstore.domain.entity.ProductDo;
import com.hn.jdstore.service.ProductService;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
    public List<Map<String, Object>> getProductPageList(Integer currentPage, Integer pageSize) {

        return productDao.getProductList((currentPage - 1) * pageSize, pageSize);
    }

    @Override
    public Long getProductPageTotal() {
        List<Map<String, Object>> productPageTotal = productDao.getProductPageTotal();

        return (Long) productPageTotal.get(0).get("total");
    }
}
