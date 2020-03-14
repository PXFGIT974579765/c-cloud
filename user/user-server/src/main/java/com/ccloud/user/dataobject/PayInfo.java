package com.ccloud.user.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/14 18:37
 * @description：支付实体
 * @modified By：
 */
@Entity
@Data
public class PayInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String orderId;

    private Integer status;

    private BigDecimal amount;
}