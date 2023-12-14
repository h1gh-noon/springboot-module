package com.hn.jdstore.service;

import com.hn.jdstore.domain.entity.ShopDo;
import com.hn.jdstore.domain.vo.ShopInfoProductVo;
import com.hn.jdstore.domain.vo.ShopVo;
import org.springframework.data.domain.Page;

public interface ShopService {


    void delete(ShopDo shopDo);

    Long update(ShopDo shopDo);

    ShopDo findById(Long id);

    Page<ShopDo> getShopPageList(Integer currentPage, Integer pageSize, ShopVo shopVo);

    Page<ShopDo> searchShopByNamePageList(Integer currentPage, Integer pageSize, String name);

    ShopInfoProductVo getShopInfoProductList(Long shopId);

}
