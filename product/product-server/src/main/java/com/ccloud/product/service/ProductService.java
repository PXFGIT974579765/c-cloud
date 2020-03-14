package com.ccloud.product.service;

import com.ccloud.common.dto.OrderDTO;
import com.ccloud.product.dataobject.ProductInfo;

import java.util.List;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/12 21:11
 * @description：
 * @modified By：
 */
public interface ProductService {

    /**
     * 查询所有在架的商品列表
     */
    List<ProductInfo> findUpAll();


    /**
     * 通过商品id集合获取商品信息
     */
    List<ProductInfo> findList(List<String> productIdList);

    /**
     * 触发解锁商品的情况
     */
    void handleError(OrderDTO dto);


}
