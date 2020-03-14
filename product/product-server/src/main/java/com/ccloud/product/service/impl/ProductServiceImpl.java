package com.ccloud.product.service.impl;

import com.ccloud.order.form.OrderForm;
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
    private ProductLockRepository lockRepository;

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
     * 商品服务接收到监听新订单消息，进行锁商品
     * @Author: 腾云先生
     * @Date: 2020/03/14 15:54
     */
    @Transactional
    @JmsListener(destination = "order:new", containerFactory = "msgFactory")
    public void handleTicketLock(OrderForm orderForm) {
        log.info("Get new order for produck lock:{}", orderForm);

        ProductLock productLock = ProductLock.builder()
                .productId(orderForm.getProductId())
                .phone(orderForm.getPhone()).build();

        // 此处是使用了数据库的事务来进行锁的机制，也可以使用分布式锁来实现
        // Lock lock = distributeLock();
        // if(lock.tryLock()){
        //    // something
        // }
        int lockCount = lockRepository.savetIngore(productLock);

        if (lockCount == 0) {
            orderForm.setStatus("PRODUCT_LOCK_FAIL");
            jmsTemplate.convertAndSend("order:fail", orderForm);
        } else {
            orderForm.setStatus("TICKET_LOCKED");
            jmsTemplate.convertAndSend("order:locked", orderForm);
        }
    }

}
