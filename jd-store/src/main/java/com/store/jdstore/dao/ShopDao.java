package com.store.jdstore.dao;

import com.store.jdstore.entity.HanmaShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopDao extends JpaRepository<HanmaShopEntity, Long> {
}
