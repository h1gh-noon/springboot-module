package com.hn.jdstore.dao;

import com.hn.jdstore.domain.entity.ProductDo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
public interface ProductDao extends JpaRepository<ProductDo, Long> {

    Page<ProductDo> findByNameLike(String name, Pageable page);

    @Query(value = "UPDATE jd_product SET product_stock=(product_stock+:#{#productDo" +
            ".productStock}) " +
            "WHERE id=:#{#productDo.id}",
            nativeQuery = true)
    @Modifying
    @Transactional
    Integer updateStock(ProductDo productDo);

    @Query(value = "SELECT p.id, p.name, p.cate_id, pc.name cateName, p.img_url, p.price, p" +
            ".old_price, p.product_stock, p.status, p.sales, p.shop_id, s.name shopName FROM " +
            "jd_shop s, jd_product p, jd_product_category pc WHERE s.status=1 AND pc.status=1 AND" +
            " s.id=p.shop_id AND p.cate_id=pc.id LIMIT :skip,:size",
            nativeQuery = true)
    @Modifying
    @Transactional
    List<Map<String, Object>> getProductList(Integer skip, Integer size);

    @Query(value = "SELECT count(p.id) total FROM jd_shop s, jd_product p, jd_product_category pc WHERE s.status=1 AND pc.status=1 AND" +
            " s.id=p.shop_id AND p.cate_id=pc.id",
            nativeQuery = true)
    @Modifying
    @Transactional
    List<Map<String, Object>> getProductPageTotal();
}
