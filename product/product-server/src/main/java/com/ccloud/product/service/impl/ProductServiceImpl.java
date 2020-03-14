package com.ccloud.product.service.impl;

import com.ccloud.common.constants.QueueNameConstant;
import com.ccloud.common.dto.OrderDTO;
import com.ccloud.common.enums.OrderStatusEnum;
import com.ccloud.product.dataobject.ProductInfo;
import com.ccloud.product.dataobject.ProductLock;
import com.ccloud.product.enums.ProductStatusEnum;
import com.ccloud.product.repository.ProductInfoRepository;
import com.ccloud.product.repository.ProductLockRepository;
import com.ccloud.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/12 23:03
 * @description：商品服务类
 * @modified By：
 */
@Service
// @CacheConfig(cacheNames = "product")
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository repository;

    @Autowired
    private ProductLockRepository productLockRepository;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public List<ProductInfo> findList(List<String> productIdList) {
        return repository.findByProductIdIn(productIdList);
    }

    /**
     * 功能描述:
     * 新订单队列
     * 商品服务接收到监听新订单消息，进行锁商品
     * @Author: 腾云先生
     * @Date: 2020/03/14 15:54
     */
    @Transactional
    @JmsListener(destination = QueueNameConstant.ORDER_NEW, containerFactory = "msgFactory")
    public void handleTicketLock(OrderDTO orderDTO) {
        log.info("Get new order for produck lock:{}", orderDTO);

        ProductLock productLock = ProductLock.builder()
                .productId(orderDTO.getProductId())
                .phone(orderDTO.getPhone()).build();

        // 此处是使用了数据库的事务来进行锁的机制，也可以使用分布式锁来实现
        // Lock lock = distributeLock();
        // if(lock.tryLock()){
        //    // something
        // }
        int lockCount = productLockRepository.savetIngore(productLock);

        if (lockCount == 0) {
            orderDTO.setOrderStatus(OrderStatusEnum.PRODUCT_LOCK_FAIL.getCode());
            // 锁商品失败，发送消息到订单失败队列
            jmsTemplate.convertAndSend(QueueNameConstant.ORDER_FAIL, orderDTO);
        } else {
            orderDTO.setOrderStatus(OrderStatusEnum.PRODUCT_LOCKED.getCode());
            // 锁商品成功，发送消息到锁商品成功队列，order服务进行创建订单
            jmsTemplate.convertAndSend(QueueNameConstant.ORDER_LOCKED, orderDTO);
        }
    }


    /**
     * 功能描述:
     *  触发解锁商品的情况
     *1. 扣费失败，需要解锁商品
     *2. 订单超时，如果存在商品被锁就解锁
     * 这时候，都已经在OrderDTO里设置了失败的原因，所以这里就不再设置原因。
     * @Author: 腾云先生
     * @Date: 2020/03/14 22:22
     */
    @Override
    @Transactional
    @JmsListener(destination = QueueNameConstant.ORDER_ERROR, containerFactory = "msgFactory")
    public void handleError(OrderDTO dto) {
        log.info("Get order error for ticket unlock:{}", dto);

        // 解锁商品
        int count = productLockRepository.unLockProduct(dto.getProductId(), dto.getPhone());
        if (count == 0) {
            log.info("product already unlocked:", dto);
        }
        jmsTemplate.convertAndSend(QueueNameConstant.ORDER_FAIL, dto);
    }

}
