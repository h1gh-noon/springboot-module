package com.hn.jdstore.dao;

import com.hn.jdstore.domain.entity.ShopDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SearchDao extends JpaRepository<ShopDo, String> {

    @Query(value = "select p.id, p.name, p.sales, p.price, p.old_price, p.product_stock, p" +
            ".img_url, s.id shopId," +
            " s.name shopName, s.img_url shopImg, s.state shopState, s.desc_detail " +
            "shopDescDetail, s.express_limit shopExpressLimit, s.express_price shopExpressPrice" +
            " from jd_shop s, jd_product p" +
            " WHERE s.id=p.shop_id and s.status=1 and p.status=1 AND p.name like ?1",
            nativeQuery = true)
    List<Map<String, Object>> searchAll(String name);

}
