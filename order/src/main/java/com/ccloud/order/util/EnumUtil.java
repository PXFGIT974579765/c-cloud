package com.ccloud.order.util;

import com.ccloud.order.enums.CodeEnum;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/12 00:22
 * @description：枚举工具类
 * @modified By：
 */
public class EnumUtil {

    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {
        for (T each : enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }
}
