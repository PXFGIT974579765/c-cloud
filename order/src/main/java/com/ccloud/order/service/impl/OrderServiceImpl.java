package com.ccloud.order.service.impl;

import com.ccloud.order.dataobject.OrderDetail;
import com.ccloud.order.dataobject.OrderMaster;
import com.ccloud.order.dto.OrderDTO;
import com.ccloud.order.enums.OrderStatusEnum;
import com.ccloud.order.enums.ResultEnum;
import com.ccloud.order.exception.AppException;
import com.ccloud.order.repository.OrderDetailRepository;
import com.ccloud.order.repository.OrderMasterRepository;
import com.ccloud.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

//    @Autowired
//    private ProductClient productClient;


    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
//        String orderId = KeyUtil.genUniqueKey();
//        //todo 查询商品信息（调用商品服务）
//        List<String> productIdList = orderDTO.getOrderDetailList().stream()
//                .map(OrderDetail::getOrderId)
//                .collect(Collectors.toList());
//        List<ProductInfoOutput> productInfoList = productClient.listForOrder(productIdList);
//        //todo 计算总价
//        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
//        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
//            for (ProductInfoOutput productInfo : productInfoList) {
//                if (productInfo.getProductId().equals(orderDetail.getProductId())) {
//                    orderAmount = productInfo.getProductPrice()
//                            .multiply(new BigDecimal(orderDetail.getProductQuantity()))
//                            .add(orderAmount);
//                    BeanUtils.copyProperties(productInfo, orderDetail);
//                    orderDetail.setOrderId(orderId);
//                    orderDetail.setDetailId(KeyUtil.genUniqueKey());
//                    orderDetailRepository.save(orderDetail);
//                }
//            }
//        }
//        //todo 扣库存（调用商品服务）
//        List<DecreaseStockInput> cartDTOList = orderDTO.getOrderDetailList().stream()
//                .map(e -> new DecreaseStockInput(e.getProductId(), e.getProductQuantity()))
//                .collect(Collectors.toList());
//        productClient.decreaseStock(cartDTOList);
//
//        // 订单入库
//        OrderMaster orderMaster = new OrderMaster();
//        orderDTO.setOrderId(orderId);
//        BeanUtils.copyProperties(orderDTO, orderMaster);
//        orderMaster.setOrderAmount(new BigDecimal(5));
//        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
//        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
//        orderMasterRepository.save(orderMaster);
     return orderDTO;
    }

    /**
     * 完结订单，只能卖家操作
     *
     * @param orderId
     * @return
     */
    @Override
    @Transactional
    public OrderDTO finish(String orderId) {
        // 先查询订单
        Optional<OrderMaster> orderMasterOptional = orderMasterRepository.findById(orderId);
        if (!orderMasterOptional.isPresent()) {
            throw new AppException(ResultEnum.ORDER_NOT_EXIST);
        }

        // 判断订单状态
        OrderMaster orderMaster = orderMasterOptional.get();
        if (OrderStatusEnum.NEW.getCode().equals(orderMaster.getOrderStatus())) {
            throw new AppException(ResultEnum.ORDER_STATUS_ERROR);
        }

        // 修改订单为完结
        orderMaster.setOrderStatus(OrderStatusEnum.FINISH.getCode());
        orderMasterRepository.save(orderMaster);

        // 查询订单详情
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderMaster.getOrderId());
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new AppException(ResultEnum.ORDER_DETAIL_EMPTY);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }


}