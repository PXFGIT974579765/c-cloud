package com.ccloud.common.enums;

import lombok.Getter;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/14 13:39
 * @description：
 * @modified By：
 */
@Getter
public enum PayStatusEnum implements CodeEnum {
    WAIT(0, "等待支付"),
    PAID(1, "支付成功");

    private Integer code;
    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public static PayStatusEnum getEnum(int value) {
        for (PayStatusEnum paymentTypeEnum : PayStatusEnum.values()) {
            if (value == paymentTypeEnum.getCode()) {
                return paymentTypeEnum;
            }
        }
        return null;
    }
}
