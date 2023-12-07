package com.hn.jdstore.dao;

import com.hn.jdstore.entity.HanmaOrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailDao extends JpaRepository<HanmaOrderDetailEntity, Long> {

    List<HanmaOrderDetailEntity> findAllByOrderId(Long orderId);
}
