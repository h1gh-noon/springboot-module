package com.store.jdstore.dao;

import com.store.jdstore.entity.HanmaOrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailDao extends JpaRepository<HanmaOrderDetailEntity, Long> {
}
