package com.hn.jdstore.dao;

import com.hn.jdstore.entity.HanmaProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao extends JpaRepository<HanmaProductEntity, Long> {
}
