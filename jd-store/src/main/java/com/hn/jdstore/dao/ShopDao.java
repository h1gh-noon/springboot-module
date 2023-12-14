package com.hn.jdstore.dao;

import com.hn.jdstore.domain.entity.HanmaShopDo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopDao extends JpaRepository<HanmaShopDo, Long> {

    Page<HanmaShopDo> findByNameLike(String name, Pageable page);

}
