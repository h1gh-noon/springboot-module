package com.hn.jdstore.dao;

import com.hn.jdstore.domain.entity.OrderDo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrderDao extends JpaRepository<OrderDo, Long> {
    OrderDo findByOrderNo(String orderNo);

    Page<OrderDo> findByShopId(Long shopId, Pageable pageable);

    Page<OrderDo> findByUserId(Long userId, Pageable pageable);

    @Query(value = "UPDATE `jd_order` SET order_status=3 WHERE id=?1", nativeQuery = true)
    @Modifying
    @Transactional
    Integer orderCancel(Long id);

}
