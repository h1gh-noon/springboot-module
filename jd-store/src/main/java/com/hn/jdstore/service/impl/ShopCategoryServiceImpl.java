package com.hn.jdstore.service.impl;

import com.hn.common.util.Util;
import com.hn.jdstore.domain.entity.ShopCategoryDo;
import com.hn.jdstore.service.ShopCategoryService;
import com.hn.jdstore.dao.ShopCategoryDao;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {

    @Resource
    private ShopCategoryDao shopCategoryDao;

    @Override
    public void delete(ShopCategoryDo shopCategoryDo) {
        shopCategoryDao.delete(shopCategoryDo);
    }

    @Override
    public Long update(ShopCategoryDo shopCategoryDo) {
        if (shopCategoryDo.getId() == null) {
            return shopCategoryDao.save(shopCategoryDo).getId();
        }
        ShopCategoryDo shopCategory = findById(shopCategoryDo.getId());
        ShopCategoryDo h = new ShopCategoryDo();
        BeanUtils.copyProperties(shopCategoryDo, h);
        BeanUtils.copyProperties(shopCategory, h);
        h.setUpdateTime(Util.getTimestampStr());
        return shopCategoryDao.save(h).getId();
    }

    @Override
    public ShopCategoryDo findById(Long id) {
        return shopCategoryDao.findById(id).orElse(null);
    }

    @Override
    public List<ShopCategoryDo> getShopCategoryList() {
        return shopCategoryDao.findAll();
    }
}
