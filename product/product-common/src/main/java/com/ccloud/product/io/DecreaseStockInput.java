package com.ccloud.product.io;

import lombok.Data;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/12 22:32
 * @description： 扣库存实体的输入
 * @modified By：
 */
@Data
public class DecreaseStockInput {

    private String productId;

    private Integer productQuantity;

    public DecreaseStockInput() {
    }

    public DecreaseStockInput(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
