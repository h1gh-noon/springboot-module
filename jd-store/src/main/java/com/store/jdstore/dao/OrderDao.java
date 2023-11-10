package com.store.jdstore.dao;

import com.store.jdstore.entity.HanmaOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDao extends JpaRepository<HanmaOrderEntity, Long> {
}
