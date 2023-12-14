package com.hn.jdstore.dao;

import com.hn.jdstore.domain.entity.ProductCategoryDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryDao extends JpaRepository<ProductCategoryDo, Long> {
}
