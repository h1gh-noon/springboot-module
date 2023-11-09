package com.store.jdstore.dao;

import com.store.jdstore.entity.HanmaOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDao extends JpaRepository<HanmaOrderEntity, Long> {
}
