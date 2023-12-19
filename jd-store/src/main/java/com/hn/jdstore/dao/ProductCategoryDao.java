package com.hn.jdstore.dao;

import com.hn.jdstore.domain.entity.ProductCategoryDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
public interface ProductCategoryDao extends JpaRepository<ProductCategoryDo, Long> {

    @Query(value = "SELECT pc.id, pc.name, pc.shop_id, s.name shopName, pc.status, pc.type, pc.create_time FROM jd_product_category pc, jd_shop s WHERE pc.shop_id=s.id", nativeQuery = true)
    @Modifying
    @Transactional
    List<Map<String, Object>> getProductCategoryList();


}
