package com.ccloud.order.util;

import java.util.UUID;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/13 00:27
 * @description：token工具类
 * @modified By：
 */
public class KeyUtil {

    public static String uuid32() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-", "");
    }
}
