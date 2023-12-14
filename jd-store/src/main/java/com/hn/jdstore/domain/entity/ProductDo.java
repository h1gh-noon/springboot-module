package com.hn.jdstore.domain.entity;

import lombok.Data;
import jakarta.persistence.*;

import java.math.BigDecimal;


@Data
@Entity
@Table(name = "jd_product")
public class ProductDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long sales;
    private BigDecimal price;
    private BigDecimal oldPrice;
    private Long productStock;
    private String imgUrl;
    private Long cateId;
    private Long shopId;
    private Integer status;
    private String createTime;
    private String updateTime;

}
