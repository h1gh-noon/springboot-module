package com.hn.jdstore.service.impl;

import com.hn.jdstore.entity.HanmaShopCategoryEntity;
import com.hn.jdstore.service.ShopCategoryService;
import com.hn.jdstore.dao.ShopCategoryDao;
import com.hn.jdstore.util.Utils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {

    @Resource
    private ShopCategoryDao shopCategoryDao;

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
