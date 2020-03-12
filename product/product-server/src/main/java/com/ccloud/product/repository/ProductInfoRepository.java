package com.ccloud.product.repository;

import com.ccloud.product.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/12 23:04
 * @description：商品持久层
 * @modified By：
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {

    List<ProductInfo> findByProductStatus(Integer productStatus);

    List<ProductInfo> findByProductIdIn(List<String> productId);
}