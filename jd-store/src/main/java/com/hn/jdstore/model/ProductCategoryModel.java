package com.hn.jdstore.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "hanma_product_category")
public class ProductCategoryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long shopId;
    private String name;
    private String type;
    private Integer status;

}
