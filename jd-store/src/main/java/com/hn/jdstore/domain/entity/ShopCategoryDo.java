package com.hn.jdstore.domain.entity;

import com.hn.common.dto.Validation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
@Entity
@Table(name = "jd_shop_category")
public class ShopCategoryDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String imgUrl;
    private Integer status;

    private String createTime;

    private String updateTime;

}
