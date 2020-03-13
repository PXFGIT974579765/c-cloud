package com.ccloud.user.constant;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/13 22:53
 * @description：redis常量模板
 * @modified By：
 */
public interface RedisConstant {

    String TOKEN_TEMPLATE = "token_%s";

    String UID_TEMPLATE = "uid_%s";

    //单位秒   半小时
    Integer expire = 60 * 30;
}
