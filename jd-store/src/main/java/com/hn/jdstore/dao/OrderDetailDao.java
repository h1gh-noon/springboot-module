package com.hn.jdstore.dao;

import com.hn.jdstore.domain.entity.OrderDetailDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailDao extends JpaRepository<OrderDetailDo, Long> {

    List<OrderDetailDo> findAllByOrderId(Long orderId);
}
