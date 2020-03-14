package com.ccloud.order.dataobject;

import com.ccloud.common.enums.OrderStatusEnum;
import com.ccloud.common.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/14 13:35
 * @description：order_master表entity
 * @modified By：
 */
@Entity
@Data
@DynamicUpdate
public class OrderMaster {

    /**
     * 订单id.
     */
    @Id
    private String orderId;

    /**
     * 买家id.
     */
    private String userId;

    /**
     * 商品id.
     */
    private String productId;


    /**
     * 买家电话.
     */
    private String phone;

    /**
     * 订单总金额.
     */
    private BigDecimal orderAmount;

    /**
     * 订单状态，默认为0 新下单.
     */
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    /**
     * 支付状态，默认为0未支付.
     */
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    /**
     * 创建时间.
     */
    private Date createTime;

    /**
     * 更新时间.
     */
    private Date updateTime;


}
