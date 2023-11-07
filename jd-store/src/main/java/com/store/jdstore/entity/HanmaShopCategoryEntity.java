package com.store.jdstore.entity;

import lombok.Data;
import jakarta.persistence.*;


@Data
@Entity
@Table(name = "hanma_shop_category")
public class HanmaShopCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String imgUrl;
    private Long status;

    @Column(insertable = false, updatable = false)
    private String createTime;

    @Column(insertable = false, updatable = false)
    private String updateTime;

}
