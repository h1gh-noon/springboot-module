package com.hn.jdstore.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "hanma_shop_category")
public class HanmaShopCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String imgUrl;
    private Integer status;

    private String createTime;

    private String updateTime;

}
