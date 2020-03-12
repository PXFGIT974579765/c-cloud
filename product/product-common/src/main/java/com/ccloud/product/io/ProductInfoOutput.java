package com.ccloud.product.io;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/12 22:35
 * @description：商品信息的输出实体
 * @modified By：
 */
@Data
public class ProductInfoOutput {

    private String productId;

    /** 名字. */
    private String productName;

    /** 单价. */
    private BigDecimal productPrice;

    /** 库存. */
    private Integer productStock;

    /** 描述. */
    private String productDescription;

    /** 小图. */
    private String productIcon;

    /** 状态, 0正常1下架. */
    private Integer productStatus;

    /** 类目编号. */
    private Integer categoryType;
}
