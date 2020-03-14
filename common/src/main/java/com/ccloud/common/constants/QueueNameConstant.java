package com.ccloud.common.constants;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/14 16:22
 * @description：消息队列名称常量模板，消除魔鬼字符串
 * @modified By：
 */
public interface QueueNameConstant {

    String ORDER_NEW = "order:new";

    String ORDER_FAIL = "order:fail";

    String ORDER_LOCKED = "order:locked";

    String ORDER_PAY = "order:pay";

    String ORDER_TICKER_ERROR = "order:ticket_error";

    String ORDER_DO_FINISH = "order:do_finish";
}
