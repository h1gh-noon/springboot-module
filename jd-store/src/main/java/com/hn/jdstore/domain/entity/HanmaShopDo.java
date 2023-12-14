package com.hn.jdstore.domain.entity;

import lombok.Data;
import jakarta.persistence.*;

import java.math.BigDecimal;


@Data
@Entity
@Table(name = "hanma_shop")
public class HanmaShopDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long cateId;
    private String name;
    private Long sales;
    private Long expressLimit;
    private BigDecimal expressPrice;
    private String descDetail;
    private String imgUrl;
    private Long state;
    private Integer status;

    private String createTime;

    private String updateTime;

}
