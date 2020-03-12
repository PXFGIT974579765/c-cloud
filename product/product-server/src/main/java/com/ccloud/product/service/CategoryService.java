package com.ccloud.product.service;

import com.ccloud.product.dataobject.ProductCategory;

import java.util.List;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/12 21:08
 * @description：
 * @modified By：
 */
public interface CategoryService {

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
