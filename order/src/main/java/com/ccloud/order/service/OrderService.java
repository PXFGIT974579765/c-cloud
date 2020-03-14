package com.ccloud.order.service;

import com.ccloud.order.dto.OrderDTO;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/14 13:44
 * @description：订单服务类接口
 * @modified By：
 */
public interface OrderService {

    /**
     * 创建订单.
     */
    OrderDTO create(OrderDTO orderDTO);


    /**
     * 完结订单.
     */
    OrderDTO finish(String orderId);

}