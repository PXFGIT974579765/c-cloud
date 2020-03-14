package com.ccloud.user.service;

import com.ccloud.common.dto.OrderDTO;
import com.ccloud.user.dataobject.UserInfo;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/13 00:03
 * @description：用户服务接口
 * @modified By：
 */
public interface UserService {

    /**
     * 通过openid查询
     * @param phone
     * @return
     */
    UserInfo findByPhone(String phone);

    void handleOrderPay(OrderDTO orderDTO);
}