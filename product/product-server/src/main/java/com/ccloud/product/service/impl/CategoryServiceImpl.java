package com.ccloud.product.service.impl;

import com.ccloud.product.dataobject.ProductCategory;
import com.ccloud.product.repository.ProductCategoryRepository;
import com.ccloud.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/12 21:09
 * @description：
 * @modified By：
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryRepository repository;

    /**
     * 功能描述:
     * 通过类目编号集合获取类目信息
     * @Author: 腾云先生
     * @Date: 2020/03/12 21:11
     */
    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return repository.findByCategoryTypeIn(categoryTypeList);
    }
}
