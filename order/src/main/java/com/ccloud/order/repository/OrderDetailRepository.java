package com.ccloud.order.repository;

import com.ccloud.order.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/14 13:43
 * @description：
 * @modified By：
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

    List<OrderDetail> findByOrderId(String orderId);
}
