package com.ccloud.order.service.impl;

import com.ccloud.common.constants.QueueNameConstant;
import com.ccloud.common.dto.OrderDTO;
import com.ccloud.common.enums.OrderStatusEnum;
import com.ccloud.common.enums.PayStatusEnum;
import com.ccloud.order.dataobject.OrderMaster;
import com.ccloud.order.repository.OrderMasterRepository;
import com.ccloud.order.service.OrderService;
import com.ccloud.order.util.KeyUtil;
import com.ccloud.product.client.ProductClient;
import com.ccloud.product.io.ProductInfoOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/14 13:45
 * @description：订单服务类实现类
 * @modified By：
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private ProductClient productClient;


    @Override
    @Transactional
    @JmsListener(destination = QueueNameConstant.ORDER_LOCKED, containerFactory = "msgFactory")
    public void create(OrderDTO orderDTO) {
        log.info("Get new order to create:{}", orderDTO);
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        // 通过保存到数据库，来使用uuid处理重复消息
        if (orderMasterRepository.findByOrderId(orderDTO.getOrderId()) != null) {
            log.info("Msg already processed:{}", orderDTO);
        } else {
            // 根据商品id查询商品信息
            List<ProductInfoOutput> productInfoList = productClient.listForOrder(Arrays.asList(orderDTO.getProductId()));
            //todo 计算总价
            for (ProductInfoOutput productInfo : productInfoList) {
                orderAmount = productInfo.getProductPrice().add(orderAmount);
            }
            OrderMaster orderMaster = new OrderMaster();
            BeanUtils.copyProperties(orderDTO, orderMaster);
            orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
            orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
            orderMaster.setOrderAmount(orderAmount);


            orderMasterRepository.save(orderMaster);

        }

        orderDTO.setOrderAmount(orderAmount);
        orderDTO.setOrderStatus(OrderStatusEnum.NEW.getCode());
        // 创建订单成功，发送消息到缴费队列，user 服务监听扣费
        jmsTemplate.convertAndSend(QueueNameConstant.ORDER_PAY, orderDTO);
    }

    /**
     * 功能描述:
     * 用户扣完费之后发送消息到订单完成消息队列
     * 由order的这个方法监听消息并完成订单
     *
     * @Author: 腾云先生
     * @Date: 2020/03/14 19:22
     */
    @Override
    @Transactional
    @JmsListener(destination = QueueNameConstant.ORDER_DO_FINISH, containerFactory = "msgFactory")
    public void finishOrder(OrderDTO orderDTO) {
        log.info("Get finished order:{}", orderDTO);

        OrderMaster orderMaster = orderMasterRepository.findByOrderId(orderDTO.getOrderId());
        orderMaster.setOrderStatus(OrderStatusEnum.FINISH.getCode());
        orderMasterRepository.save(orderMaster);
    }

    /**
     * 功能描述:
     * 处理失败订单：
     * 1. 一开始索商品失败。
     * 2. 扣费失败后，解锁商品，然后触发
     * 3. 定时任务检测到订单超时
     *
     * @Author: 腾云先生
     * @Date: 2020/03/14 21:58
     */
    @Override
    @Transactional
    @JmsListener(destination = QueueNameConstant.ORDER_FAIL, containerFactory = "msgFactory")
    public void handleFailed(OrderDTO dto) {
        log.info("Get failed order:{}", dto);
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        OrderMaster orderMaster;

        // 还没来的及生成orderId,表示锁商品就失败了
        if (dto.getOrderId() == null) {
            // 根据商品id查询商品信息
            List<ProductInfoOutput> productInfoList = productClient.listForOrder(Arrays.asList(dto.getProductId()));
            //todo 计算总价
            for (ProductInfoOutput productInfo : productInfoList) {
                orderAmount = productInfo.getProductPrice().add(orderAmount);
            }

            orderMaster = new OrderMaster();
            BeanUtils.copyProperties(dto, orderMaster);
            orderMaster.setOrderAmount(orderAmount);
            orderMaster.setOrderId(KeyUtil.uuid32());
            orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
            orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
            // 锁票失败，可能票id不对，或者已被别人买走
            orderMaster.setReason("TICKET_LOCK_FAIL");
        } else {
            orderMaster = orderMasterRepository.findByOrderId(dto.getOrderId());

            // 因为余额不足扣费失败导致的订单失败
            if (OrderStatusEnum.NOT_ENOUGH_DEPOSIT.getMessage().equals(dto.getOrderStatus())) {
                orderMaster.setReason("NOT_ENOUGH_DEPOSIT");
            }
            // 因为超时
            if (OrderStatusEnum.TIME_OUT.getMessage().equals(dto.getOrderStatus())) {
                orderMaster.setReason("TIME_OUT");
            }
        }
        orderMaster.setOrderStatus(OrderStatusEnum.FAIL.getCode());
        orderMasterRepository.save(orderMaster);
    }


    /**
     * 功能描述:
     * 定时检查失败的订单，通过超时来判定
     * 此处暂且不考虑分布式任务调度系统
     * @Author: 腾云先生
     * @Date: 2020/03/14 22:41
     */
    @Scheduled(fixedDelay = 10000L)
    public void checkInvalidOrder() {
        // 设置一分钟的超时时间
        ZonedDateTime checkTime = ZonedDateTime.now().minusMinutes(1L);

        List<OrderMaster> orders = orderMasterRepository.findAllByOrderStatusAndCreateTimeBefore(OrderStatusEnum.NEW.getCode(), checkTime);

        orders.stream().forEach(order -> {
            log.error("Order timeout:{}", order);

            OrderDTO dto = new OrderDTO();
            dto.setOrderId(order.getOrderId());
            dto.setProductId(order.getProductId());
            dto.setPhone(order.getPhone());
            dto.setOrderAmount(order.getOrderAmount());
            dto.setUserId(order.getUserId());
            dto.setOrderStatus(OrderStatusEnum.TIME_OUT.getCode());

            jmsTemplate.convertAndSend(QueueNameConstant.ORDER_ERROR, dto);
        });
    }

}