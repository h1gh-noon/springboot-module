package com.hn.jdstore.dao;

import com.hn.jdstore.domain.entity.ShopDo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopDao extends JpaRepository<ShopDo, Long> {

    Page<ShopDo> findByNameLike(String name, Pageable page);

}
