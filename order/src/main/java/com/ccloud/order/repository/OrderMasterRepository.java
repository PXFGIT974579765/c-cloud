package com.ccloud.order.repository;

import com.ccloud.order.dataobject.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/14 13:43
 * @description：
 * @modified By：
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {

    OrderMaster findByOrderId(String orderId);

}