package com.hn.jdstore.service.impl;

import com.hn.common.util.Util;
import com.hn.jdstore.domain.entity.HanmaShopCategoryDo;
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
    public void delete(HanmaShopCategoryDo hanmaShopCategory) {
        shopCategoryDao.delete(hanmaShopCategory);
    }

    @Override
    public Long update(HanmaShopCategoryDo hanmaShopCategory) {
        if (hanmaShopCategory.getId() == null) {
            return shopCategoryDao.save(hanmaShopCategory).getId();
        }
        HanmaShopCategoryDo shopCategoryDo = findById(hanmaShopCategory.getId());
        HanmaShopCategoryDo h = new HanmaShopCategoryDo();
        BeanUtils.copyProperties(shopCategoryDo, h);
        BeanUtils.copyProperties(hanmaShopCategory, h);
        h.setCreateTime(shopCategoryDo.getCreateTime());
        h.setUpdateTime(Util.getTimestampStr());
        return shopCategoryDao.save(h).getId();
    }

    @Override
    public HanmaShopCategoryDo findById(Long id) {
        return shopCategoryDao.findById(id).orElse(null);
    }

    @Override
    public List<HanmaShopCategoryDo> getShopCategoryList() {
        return shopCategoryDao.findAll();
    }
}
