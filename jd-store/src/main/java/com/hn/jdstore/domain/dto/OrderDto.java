package com.hn.jdstore.domain.dto;

import com.hn.jdstore.domain.entity.HanmaOrderDetailDo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDto {

    private Long id;
    private String orderNo;
    private Long userId;
    private String userName;
    private String userPhone;
    private String userAddress;
    private String userOpenid;
    private Long shopId;
    private String shopName;
    private BigDecimal orderAmount;
    private String payTime;
    private Integer payStatus;
    private Integer orderStatus;
    private String createTime;
    private String updateTime;

    private List<HanmaOrderDetailDo> detailList;
}
