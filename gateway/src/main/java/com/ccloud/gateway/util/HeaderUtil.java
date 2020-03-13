package com.ccloud.gateway.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/13 22:34
 * @description：header工具类
 * @modified By：
 */
public class HeaderUtil {

    /**
     * 功能描述:
     * 在响应头里设置数据
     * @Author: 腾云先生
     * @Date: 2020/03/13 22:35
     */
    public static void set(HttpServletResponse response,
                           String name,
                           String value) {
        response.setHeader(name, value);
    }

    /**
     * 功能描述:
     *  从请求头里获取数据
     * @Author: 腾云先生
     * @Date: 2020/03/13 22:34
     */
    public static String get(HttpServletRequest request, String name) {
        return request.getHeader(name);
    }

}
