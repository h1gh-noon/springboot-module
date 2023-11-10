package com.store.jdstore.dao;

import com.store.jdstore.entity.HanmaProductCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryDao extends JpaRepository<HanmaProductCategoryEntity, Long> {
}
