package com.hn.jdstore.entity;

import com.hn.common.dto.Validation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
@Entity
@Table(name = "hanma_shop_category")
public class HanmaShopCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = {Validation.Update.class})
    private Long id;

    @NotBlank(groups = {Validation.Save.class})
    private String name;
    private String imgUrl;
    private Integer status;

    private String createTime;

    private String updateTime;

}
