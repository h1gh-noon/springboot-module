package com.hn.jdstore.dao;

import com.hn.jdstore.domain.entity.HanmaShopCategoryDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopCategoryDao extends JpaRepository<HanmaShopCategoryDo, Long> {
}
