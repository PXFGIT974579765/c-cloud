package com.ccloud.order.repository;

import com.ccloud.order.dataobject.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/14 13:43
 * @description：
 * @modified By：
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {

    OrderMaster findByOrderId(String orderId);

    List<OrderMaster> findByUserId(String userId);


    List<OrderMaster> findAllByOrderStatusAndCreateTimeBefore(Integer status, ZonedDateTime checkTime);


}