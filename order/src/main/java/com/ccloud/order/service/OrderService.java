package com.ccloud.order.service;


import com.ccloud.common.dto.OrderDTO;

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
    void create(OrderDTO orderDTO);

    /**
     * 完成订单.
     */
    void finishOrder(OrderDTO orderDTO);


}