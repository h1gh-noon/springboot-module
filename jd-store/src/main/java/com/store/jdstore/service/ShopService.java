package com.store.jdstore.service;

import com.store.jdstore.entity.HanmaShopEntity;
import com.store.jdstore.model.ShopModel;

import java.util.List;

public interface ShopService {


    void delete(HanmaShopEntity hanmaShopEntity);

    Long update(HanmaShopEntity hanmaShopEntity);

    HanmaShopEntity findById(Long id);

    List<HanmaShopEntity> getShopPageList(ShopModel shopModel);

}
