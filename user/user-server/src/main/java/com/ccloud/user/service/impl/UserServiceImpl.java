package com.ccloud.user.service.impl;

import com.ccloud.common.constants.QueueNameConstant;
import com.ccloud.common.dto.OrderDTO;
import com.ccloud.common.enums.OrderStatusEnum;
import com.ccloud.common.enums.PayStatusEnum;
import com.ccloud.user.dataobject.PayInfo;
import com.ccloud.user.dataobject.UserInfo;
import com.ccloud.user.repository.PayInfoRepository;
import com.ccloud.user.repository.UserInfoRepository;
import com.ccloud.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/13 00:04
 * @description：用户服务实现类
 * @modified By：
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PayInfoRepository payInfoRepository;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public UserInfo findByPhone(String phone) {
        return userInfoRepository.findByPhone(phone);
    }

    /**
     * 功能描述:
     * 监听扣费消息队列，做扣费操作
     *
     * @Author: 腾云先生
     * @Date: 2020/03/14 18:34
     */
    @Override
    @Transactional
    @JmsListener(destination = QueueNameConstant.ORDER_PAY, containerFactory = "msgFactory")
    public void handleOrderPay(OrderDTO orderDTO) {
        log.info("Get new order to pay:{}", orderDTO);
        // 先检查payInfo判断重复消息。
        PayInfo pay = payInfoRepository.findOneByOrderId(orderDTO.getOrderId());
        if (pay != null) {
            log.warn("Order already paid, duplicated message.");
        } else {
            UserInfo customer = userInfoRepository.findById(orderDTO.getUserId()).get();
            if (customer.getDeposit().compareTo(orderDTO.getOrderAmount()) == -1) {
                log.info("No enough deposit, need amount:{}", orderDTO.getOrderAmount());
                orderDTO.setOrderStatus(OrderStatusEnum.NOT_ENOUGH_DEPOSIT.getCode());
                // 账户余额不足，扣费失败，发送消息到买票出错队列
                jmsTemplate.convertAndSend(QueueNameConstant.ORDER_TICKER_ERROR, orderDTO);
                return;
            }

            pay = new PayInfo();
            pay.setOrderId(orderDTO.getOrderId());
            pay.setAmount(orderDTO.getOrderAmount());
            pay.setStatus(PayStatusEnum.PAID.getCode());
            payInfoRepository.save(pay);
//        如果用户下了2个订单，这个handle方法不是单线程处理，或者有多个实例，又刚好这2个请求被同时处理，
//        customer.setDeposit(customer.getDeposit() - msg.getAmount());
//        customerRepository.save(customer);
            userInfoRepository.charge(orderDTO.getUserId(), orderDTO.getOrderAmount());

        }
        orderDTO.setOrderStatus(PayStatusEnum.PAID.getCode());
        // 扣费成功 ，发送消息到订单准完成队列，由order 服务完成订单
        jmsTemplate.convertAndSend(QueueNameConstant.ORDER_DO_FINISH, orderDTO);
    }
}
