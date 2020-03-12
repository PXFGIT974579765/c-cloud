package com.ccloud.product.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/12 23:00
 * @description：商品信息
 * @modified By：
 */
@Data
public class ProductInfoVo implements Serializable {

    private static final long serialVersionUID = 6252988694993747352L;

    @JsonProperty("id")
    private String productId;

    @JsonProperty("name")
    private String productName;

    @JsonProperty("price")
    private BigDecimal productPrice;

    @JsonProperty("description")
    private String productDescription;

    @JsonProperty("icon")
    private String productIcon;


}