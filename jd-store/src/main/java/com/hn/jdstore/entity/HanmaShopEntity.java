package com.hn.jdstore.entity;

import lombok.Data;
import jakarta.persistence.*;

import java.math.BigDecimal;


@Data
@Entity
@Table(name = "hanma_shop")
public class HanmaShopEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long cateId;
    private String name;
    private Long sales;
    private Long expressLimit;
    private BigDecimal expressPrice;
    private String desc;
    private String imgUrl;
    private Long state;
    private Integer status;

    private String createTime;

    private String updateTime;

}
