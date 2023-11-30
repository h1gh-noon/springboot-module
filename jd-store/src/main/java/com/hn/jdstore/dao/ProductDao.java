package com.hn.jdstore.dao;

import com.hn.jdstore.entity.HanmaProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao extends JpaRepository<HanmaProductEntity, Long> {

    Page<HanmaProductEntity> findByNameLike(String name, Pageable page);
}
