package com.store.jdstore.dao;

import com.store.jdstore.entity.HanmaProductCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryDao extends JpaRepository<HanmaProductCategoryEntity, Long> {
}
