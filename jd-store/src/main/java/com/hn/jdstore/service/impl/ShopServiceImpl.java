package com.hn.jdstore.service.impl;

import com.hn.common.util.Util;
import com.hn.jdstore.dao.ShopDao;
import com.hn.jdstore.domain.entity.ProductCategoryDo;
import com.hn.jdstore.domain.entity.ProductDo;
import com.hn.jdstore.domain.entity.ShopDo;
import com.hn.jdstore.domain.vo.ProductCategoryVo;
import com.hn.jdstore.domain.vo.ProductVo;
import com.hn.jdstore.domain.vo.ShopInfoProductVo;
import com.hn.jdstore.domain.vo.ShopVo;
import com.hn.jdstore.service.ProductCategoryService;
import com.hn.jdstore.service.ProductService;
import com.hn.jdstore.service.ShopService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShopServiceImpl implements ShopService {

    @Resource
    private ShopDao shopDao;

    @Resource
    private ProductCategoryService productCategoryService;

    @Resource
    private ProductService productService;

    @Override
    public void delete(ShopDo shopDo) {
        shopDao.delete(shopDo);
    }

    @Override
    public Long update(ShopDo shopDo) {
        if (shopDo.getId() == null) {
            return shopDao.save(shopDo).getId();
        }

        ShopDo h = new ShopDo();
        ShopDo old = findById(shopDo.getId());
        BeanUtils.copyProperties(old, h);
        BeanUtils.copyProperties(shopDo, h);

        h.setCreateTime(old.getCreateTime());
        h.setUpdateTime(Util.getTimestampStr());

        return shopDao.save(h).getId();

    }

    @Override
    public ShopDo findById(Long id) {
        return shopDao.findById(id).orElse(null);
    }

    @Override
    public Page<ShopDo> getShopPageList(Integer currentPage, Integer pageSize, ShopVo shopVo) {
        ShopDo h = new ShopDo();
        if (shopVo != null) {
            BeanUtils.copyProperties(shopVo, h);
        }
        Example<ShopDo> example = Example.of(h);
        Sort sort = Sort.by(Sort.Direction.DESC, "sales");

        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, sort);
        return shopDao.findAll(example, pageable);
    }

    @Override
    public List<ShopDo> getShopList(ShopVo shopVo) {
        ShopDo h = new ShopDo();
        if (shopVo != null) {
            BeanUtils.copyProperties(shopVo, h);
        }
        Example<ShopDo> example = Example.of(h);
        Sort sort = Sort.by(Sort.Direction.DESC, "sales");
        return shopDao.findAll(example, sort);
    }

    @Override
    public Page<ShopDo> searchShopByNamePageList(Integer currentPage, Integer pageSize,
                                                 String name) {
        Sort sort = Sort.by(Sort.Direction.DESC, "sales");

        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, sort);
        return shopDao.findByNameLike("%" + name + "%", pageable);
    }

    @Override
    public ShopInfoProductVo getShopInfoProductList(Long shopId) {

        ShopDo shopDo = findById(shopId);
        List<ProductCategoryDo> productCategoryDoList = productCategoryService.findByShopId(shopId);
        List<ProductDo> productDoList = productService.getProductListByShopId(shopId);

        ShopVo shopVo = new ShopVo();
        BeanUtils.copyProperties(shopDo, shopVo);

        List<ProductCategoryVo> productCategoryVos = productCategoryDoList.stream().map(p -> {
            ProductCategoryVo pcm = new ProductCategoryVo();
            BeanUtils.copyProperties(p, pcm);
            return pcm;
        }).collect(Collectors.toList());

        List<ProductVo> productVos = productDoList.stream().map(e -> {
            ProductVo p = new ProductVo();
            BeanUtils.copyProperties(e, p);
            return p;
        }).collect(Collectors.toList());

        return new ShopInfoProductVo(shopVo, productCategoryVos, productVos);
    }
}
