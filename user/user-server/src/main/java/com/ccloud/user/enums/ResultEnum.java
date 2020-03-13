package com.ccloud.user.enums;

import lombok.Getter;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/13 00:16
 * @description： 返回状态枚举类
 * @modified By：
 */
@Getter
public enum ResultEnum {

    TOKEN_ERROR(1001, "token过期或无效"),

    LOGIN_FAIL(1002, "登录失败"),

    PHONE_EMPTY(1003, "电话为空"),

    PWD_EMPTY(1004, "密码为空"),
    ;
    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}