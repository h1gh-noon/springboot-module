package com.store.jdstore.service;

import com.store.jdstore.entity.HanmaShopEntity;
import com.store.jdstore.model.Pagination;
import com.store.jdstore.model.ShopInfoProductModel;
import com.store.jdstore.model.ShopModel;
import org.springframework.data.domain.Page;

public interface ShopService {


    void delete(HanmaShopEntity hanmaShopEntity);

    Long update(HanmaShopEntity hanmaShopEntity);

    HanmaShopEntity findById(Long id);

    Page<HanmaShopEntity> getShopPageList(Pagination<ShopModel> pagination, ShopModel shopModel);

    ShopInfoProductModel getShopInfoProductList(Long shopId);

}
