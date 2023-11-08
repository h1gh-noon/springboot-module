package com.store.jdstore.dao;

import com.store.jdstore.entity.HanmaProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDao extends JpaRepository<HanmaProductEntity, Long> {
}
