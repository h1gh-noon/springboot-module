package com.hn.jdstore.service.impl;

import com.hn.jdstore.entity.HanmaProductEntity;
import com.hn.jdstore.entity.HanmaShopEntity;
import com.hn.jdstore.model.*;
import com.hn.jdstore.service.ProductCategoryService;
import com.hn.jdstore.service.ProductService;
import com.hn.jdstore.service.ShopService;
import com.hn.jdstore.dao.ShopDao;
import com.hn.jdstore.util.Utils;
import com.hn.jdstore.entity.HanmaProductCategoryEntity;
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
    public void delete(HanmaShopEntity hanmaShopEntity) {
        shopDao.delete(hanmaShopEntity);
    }

    @Override
    public Long update(HanmaShopEntity hanmaShopEntity) {
        if (hanmaShopEntity.getId() == null) {
            return shopDao.save(hanmaShopEntity).getId();
        }

        HanmaShopEntity h = new HanmaShopEntity();
        HanmaShopEntity old = findById(hanmaShopEntity.getId());
        BeanUtils.copyProperties(old, h);
        BeanUtils.copyProperties(hanmaShopEntity, h);

        h.setCreateTime(old.getCreateTime());
        h.setUpdateTime(Utils.getTimestampStr());

        return shopDao.save(h).getId();

    }

    @Override
    public HanmaShopEntity findById(Long id) {
        return shopDao.findById(id).orElse(null);
    }

    @Override
    public Page<HanmaShopEntity> getShopPageList(Pagination<ShopModel> pagination, ShopModel shopModel) {
        HanmaShopEntity h = new HanmaShopEntity();
        if (shopModel != null) {
            h.setCateId(shopModel.getCateId());
            h.setState(shopModel.getState());
        }
        Example<HanmaShopEntity> example = Example.of(h);
        Sort sort = Sort.by(Sort.Direction.DESC, "sales");

        Pageable pageable = PageRequest.of(pagination.getCurrentPage() - 1, pagination.getPageSize(), sort);
        return shopDao.findAll(example, pageable);
    }

    @Override
    public ShopInfoProductModel getShopInfoProductList(Long shopId) {

        HanmaShopEntity shopEntity = findById(shopId);
        List<HanmaProductCategoryEntity> productCategoryEntityList = productCategoryService.findByShopId(shopId);
        List<HanmaProductEntity> productEntityList = productService.getProductListByShopId(shopId);

        ShopModel shopModel = new ShopModel();
        BeanUtils.copyProperties(shopEntity, shopModel);

        List<ProductCategoryModel> productCategoryModels = productCategoryEntityList.stream().map(p -> {
            ProductCategoryModel pcm = new ProductCategoryModel();
            BeanUtils.copyProperties(p, pcm);
            return pcm;
        }).collect(Collectors.toList());

        List<ProductModel> productModels = productEntityList.stream().map(e -> {
            ProductModel p = new ProductModel();
            BeanUtils.copyProperties(e, p);
            return p;
        }).collect(Collectors.toList());

        return new ShopInfoProductModel(shopModel, productCategoryModels, productModels);
    }
}
