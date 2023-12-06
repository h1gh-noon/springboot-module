package com.hn.jdstore.dao;

import com.hn.jdstore.entity.HanmaOrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDao extends JpaRepository<HanmaOrderEntity, Long> {
    HanmaOrderEntity findByOrderNo(String orderNo);

    Page<HanmaOrderEntity> findByShopId(Long shopId, Pageable pageable);

    Page<HanmaOrderEntity> findByUserId(Long userId, Pageable pageable);

}
