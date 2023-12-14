package com.hn.jdstore.dao;

import com.hn.jdstore.domain.entity.HanmaProductDo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductDao extends JpaRepository<HanmaProductDo, Long> {

    Page<HanmaProductDo> findByNameLike(String name, Pageable page);

    @Query(value = "UPDATE hanma_product SET product_stock=(product_stock+:#{#productDo" +
            ".productStock}) " +
            "WHERE id=:#{#productDo.id}",
            nativeQuery = true)
    @Modifying
    @Transactional
    Integer updateStock(HanmaProductDo productDo);
}
