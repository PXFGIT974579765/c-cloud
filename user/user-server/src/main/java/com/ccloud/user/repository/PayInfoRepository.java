package com.ccloud.user.repository;

import com.ccloud.user.dataobject.PayInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/13 00:02
 * @description：用户持久层
 * @modified By：
 */
public interface PayInfoRepository extends JpaRepository<PayInfo, Integer> {

    PayInfo findOneByOrderId(String orderId);

}
