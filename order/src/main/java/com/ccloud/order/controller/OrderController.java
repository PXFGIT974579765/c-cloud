package com.ccloud.order.controller;

import com.ccloud.common.constants.QueueNameConstant;
import com.ccloud.common.dto.OrderDTO;
import com.ccloud.order.enums.ResultEnum;
import com.ccloud.order.form.OrderForm;
import com.ccloud.order.service.OrderService;
import com.ccloud.order.util.BeanValidator;
import com.ccloud.order.util.KeyUtil;
import com.ccloud.order.util.ResultVOUtil;
import com.ccloud.order.vo.ResultVo;
import com.ccloud.product.client.ProductClient;
import com.ccloud.product.io.ProductInfoOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/14 13:48
 * @description：订单控制层
 * @modified By：
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class OrderController {


    @Autowired
    private OrderService orderService;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private ProductClient productClient;

    /**
     * 1、下单接口
     */
    @PostMapping("create")
    public ResultVo<Map<String, String>> create(@RequestBody OrderForm orderForm) throws InterruptedException {
        BeanValidator.check(orderForm);

        List<ProductInfoOutput> productList = productClient.listForOrder(Arrays.asList(orderForm.getProductId()));

        // 商品不存在则返回
        if(CollectionUtils.isEmpty(productList)) {
            return ResultVOUtil.error(ResultEnum.PRODUCT_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();

        // 此处生成全局唯一uuid,避免后续的消息重复消费，如果对全局id的要求很高，可以使用 ip+实例id+线程id+随机数来保证唯一性
        // 此处要求不是很高，所以用uuid
        orderDTO.setOrderId(KeyUtil.uuid32());
        orderDTO.setProductId(orderForm.getProductId());
        orderDTO.setPhone(orderForm.getPhone());
        orderDTO.setUserId(orderForm.getUserId());

        // 新订单队列，product服务锁商品
        jmsTemplate.convertAndSend(QueueNameConstant.ORDER_NEW, orderDTO);
        return ResultVOUtil.success(orderDTO.getOrderId());
    }

    @GetMapping("/{userId}")
    public ResultVo findByUserId(@PathVariable("userId") String userId) {
        return ResultVOUtil.success(orderService.findByUserId(userId));
    }

}
