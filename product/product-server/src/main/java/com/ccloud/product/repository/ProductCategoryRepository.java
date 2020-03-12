package com.ccloud.product.repository;

import com.ccloud.product.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/12 00:12
 * @description：商品类目持久层
 * @modified By：
 */
public interface ProductCategoryRepository  extends JpaRepository<ProductCategory, Integer> {

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
