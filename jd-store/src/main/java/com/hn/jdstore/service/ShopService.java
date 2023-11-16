package com.hn.jdstore.service;

import com.hn.common.model.PaginationData;
import com.hn.jdstore.entity.HanmaShopEntity;
import com.hn.jdstore.model.ShopInfoProductModel;
import com.hn.jdstore.model.ShopModel;
import org.springframework.data.domain.Page;

public interface ShopService {


    void delete(HanmaShopEntity hanmaShopEntity);

    Long update(HanmaShopEntity hanmaShopEntity);

    HanmaShopEntity findById(Long id);

    Page<HanmaShopEntity> getShopPageList(PaginationData<ShopModel> pagination, ShopModel shopModel);

    ShopInfoProductModel getShopInfoProductList(Long shopId);

}
