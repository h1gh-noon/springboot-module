package com.store.jdstore.service.impl;

import com.store.jdstore.dao.ShopCategoryDao;
import com.store.jdstore.entity.HanmaShopCategoryEntity;
import com.store.jdstore.service.ShopCategoryService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class ShopCategoryServiceImpl implements ShopCategoryService {

    private ShopCategoryDao shopCategoryDao;

    public ShopCategoryServiceImpl(ShopCategoryDao shopCategoryDao) {
        this.shopCategoryDao = shopCategoryDao;
    }

    @Override
    public void delete(HanmaShopCategoryEntity hanmaShopCategory) {
        shopCategoryDao.delete(hanmaShopCategory);
    }

    @Override
    public Long update(HanmaShopCategoryEntity hanmaShopCategory) {
        return shopCategoryDao.save(hanmaShopCategory).getId();
    }

    @Override
    public HanmaShopCategoryEntity findById(Long id) {
        return shopCategoryDao.findById(id).orElse(null);
    }

    @Override
    public List<HanmaShopCategoryEntity> getShopCategoryList() {
        return shopCategoryDao.findAll();
    }
}
