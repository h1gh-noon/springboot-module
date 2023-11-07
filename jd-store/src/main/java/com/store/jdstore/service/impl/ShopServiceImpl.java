package com.store.jdstore.service.impl;

import com.store.jdstore.dao.ShopDao;
import com.store.jdstore.entity.HanmaShopEntity;
import com.store.jdstore.model.Pagination;
import com.store.jdstore.model.ShopModel;
import com.store.jdstore.service.ShopService;
import lombok.Data;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@Data
public class ShopServiceImpl implements ShopService {

    private ShopDao shopDao;

    public ShopServiceImpl(ShopDao shopDao) {
        this.shopDao = shopDao;
    }

    @Override
    public void delete(HanmaShopEntity hanmaShopEntity) {
        shopDao.delete(hanmaShopEntity);
    }

    @Override
    public Long update(HanmaShopEntity hanmaShopEntity) {
        return shopDao.save(hanmaShopEntity).getId();
    }

    @Override
    public HanmaShopEntity findById(Long id) {
        return shopDao.findById(id).orElse(null);
    }

    @Override
    public Page<HanmaShopEntity> getShopPageList(Pagination pagination, ShopModel shopModel) {
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
}
