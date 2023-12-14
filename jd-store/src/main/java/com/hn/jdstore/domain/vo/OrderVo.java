package com.hn.jdstore.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


@Data
public class OrderVo {

    @Schema(description = "订单id")
    private Long id;

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "所属用户id")
    private Long userId;

    @Schema(description = "收件人名")
    private String userName;

    @Schema(description = "收件人电话")
    private String userPhone;

    @Schema(description = "收件人地址")
    private String userAddress;

    @Schema(description = "下单用户openid")
    private String userOpenid;

    @Schema(description = "店铺id")
    private Long shopId;

    @Schema(description = "店铺名")
    private String shopName;

    @Schema(description = "订单金额")
    private BigDecimal orderAmount;

    @Schema(description = "支付时间")
    private String payTime;

    @Schema(description = "支付状态 0待支付 1已支付")
    private Integer payStatus;

    @Schema(description = "订单状态 0未支付 1已支付 2已取消 3未发货 4已发货")
    private Integer orderStatus;

    @Schema(description = "订单时间")
    private String createTime;
    private String updateTime;

    private List<OrderDetailVo> detailList;

}
