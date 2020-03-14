package com.ccloud.common.enums;

import lombok.Getter;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/14 13:37
 * @description：
 * @modified By：
 */
@Getter
public enum OrderStatusEnum implements CodeEnum {
    NEW(0, "新订单"),
    FINISH(1, "完结"),
    CANCEL(2, "已取消"),
    PRODUCT_LOCK_FAIL(3, "锁商品失败"),
    PRODUCT_LOCKED(4, "锁商品成功"),
    NOT_ENOUGH_DEPOSIT(5,"账户余额不足"),
    PAID(6,"已支付");

    private Integer code;
    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

