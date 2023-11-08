package com.store.jdstore.service.impl;

import com.store.jdstore.dao.ShopCategoryDao;
import com.store.jdstore.entity.HanmaShopCategoryEntity;
import com.store.jdstore.service.ShopCategoryService;
import com.store.jdstore.util.Utils;
import lombok.Data;
import org.springframework.beans.BeanUtils;
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
        if (hanmaShopCategory.getId() == null) {
            return shopCategoryDao.save(hanmaShopCategory).getId();
        }
        HanmaShopCategoryEntity shopCategoryEntity = findById(hanmaShopCategory.getId());
        HanmaShopCategoryEntity h = new HanmaShopCategoryEntity();
        BeanUtils.copyProperties(shopCategoryEntity, h);
        BeanUtils.copyProperties(hanmaShopCategory, h);
        h.setCreateTime(shopCategoryEntity.getCreateTime());
        h.setUpdateTime(Utils.getTimestampStr());
        return shopCategoryDao.save(h).getId();
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
