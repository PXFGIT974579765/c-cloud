package com.ccloud.product.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/12 22:59
 * @description：按类目分类的商品信息
 * @modified By：
 */
@Data
public class ProductVo implements Serializable {

    private static final long serialVersionUID = -5385661713318383938L;

    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVo> productInfoVoList;
}