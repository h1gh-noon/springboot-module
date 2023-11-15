package com.hn.jdstore.dao;

import com.hn.jdstore.entity.HanmaOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDao extends JpaRepository<HanmaOrderEntity, Long> {
}
