package com.ccloud.common.dto;


import com.ccloud.common.serializer.Date2LongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/14 13:28
 * @description：order实体中间业务传递实体
 * @modified By：
 */
@Data
public class OrderDTO {
    /**
     * 订单id.
     */
    private String orderId;

    /**
     * 买家名字.
     */
    private String username;
    /**
     * 买家id
     */
    private String userId;

    /**
     * 买家电话.
     */
    private String phone;

    /**
     * 商品id.
     */
    private String productId;

    /**
     * 订单总金额.
     */
    private BigDecimal orderAmount;

    /**
     * 订单状态，默认为0 新下单.
     */
    private Integer orderStatus;

    /**
     * 支付状态，默认为0未支付.
     */
    private Integer payStatus;

    /**
     * 创建时间.
     */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /**
     * 更新时间.
     */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    /**
     * 订单状态文字说明.
     */
    private String orderStatusStr;
    /**
     * 订单支付状态文字说明.
     */
    private String payStatusStr;
}