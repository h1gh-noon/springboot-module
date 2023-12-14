package com.hn.jdstore.service;

import com.hn.jdstore.domain.entity.HanmaShopDo;
import com.hn.jdstore.domain.vo.ShopInfoProductVo;
import com.hn.jdstore.domain.vo.ShopVo;
import org.springframework.data.domain.Page;

public interface ShopService {


    void delete(HanmaShopDo shopDo);

    Long update(HanmaShopDo shopDo);

    HanmaShopDo findById(Long id);

    Page<HanmaShopDo> getShopPageList(Integer currentPage, Integer pageSize, ShopVo shopVo);

    Page<HanmaShopDo> searchShopByNamePageList(Integer currentPage, Integer pageSize, String name);

    ShopInfoProductVo getShopInfoProductList(Long shopId);

}
