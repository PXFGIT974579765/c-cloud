package com.ccloud.order.controller;

import com.ccloud.order.dto.OrderDTO;
import com.ccloud.order.form.OrderForm;
import com.ccloud.order.service.OrderService;
import com.ccloud.order.util.ResultVOUtil;
import com.ccloud.order.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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

    /**
     * 1、下单接口
     */
    @RequestMapping("create")
    public ResultVo<Map<String, String>> create(@Valid OrderForm orderForm,
                                                BindingResult bindingResult) throws InterruptedException {
//        if (bindingResult.hasErrors()) {
//            log.error("【创建订单】参数不正确，orderForm={}", orderForm);
//            throw new AppException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
//        }
//
//        OrderDTO orderDTO = OrderForm2OrderDTOConverter.conver(orderForm);
//        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
//            log.error("【创建订单】购物车不能为空");
//            throw new AppException(ResultEnum.CART_EMPTY);
//        }
//
//        // 等老板提出优化速度时
//        // 就将此睡眠代码去掉
//        // Thread.sleep(2000);
//
//        OrderDTO createResult = orderService.create(orderDTO);
//        Map<String, String> map = new HashMap<>();
//        map.put("orderId", createResult.getOrderId());

        return ResultVOUtil.success();
    }

    @PostMapping("/finish")
    public ResultVo<OrderDTO> finish(@RequestParam("orderId") String orderId) {
        return ResultVOUtil.success(orderService.finish(orderId));
    }
}
