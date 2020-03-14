package com.ccloud.order.enums;

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
    CANCEL(2, "已取消");

    private Integer code;
    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

