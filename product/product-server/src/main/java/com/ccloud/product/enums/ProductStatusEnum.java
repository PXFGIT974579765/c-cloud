package com.ccloud.product.enums;

import lombok.Getter;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/12 00:18
 * @description：
 * @modified By：
 */
@Getter
public enum ProductStatusEnum  implements CodeEnum{
    UP(0, "在架"),
    DOWN(1, "下架");

    private Integer code;

    private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
