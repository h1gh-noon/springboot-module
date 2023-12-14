package com.hn.jdstore.domain.entity;

import lombok.Data;
import jakarta.persistence.*;


@Data
@Entity
@Table(name = "jd_product_category")
public class ProductCategoryDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long shopId;
    private String name;
    private String type;
    private Integer status;

    private String createTime;

    private String updateTime;

}
