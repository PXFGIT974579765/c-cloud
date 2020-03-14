package com.ccloud.order.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/14 13:49
 * @description：订单创建表单
 * @modified By：
 */
@Data
public class OrderForm {

    @NotEmpty(message = "买家id必填")
    private String userId;

    @NotEmpty(message = "手机号必填")
    private String phone;


    @NotEmpty(message = "购物车不能为空")
    private String productId;

}