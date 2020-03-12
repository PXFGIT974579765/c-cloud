package com.ccloud.product.service.impl;

import com.ccloud.product.dataobject.ProductInfo;
import com.ccloud.product.enums.ProductStatusEnum;
import com.ccloud.product.io.DecreaseStockInput;
import com.ccloud.product.io.ProductInfoOutput;
import com.ccloud.product.repository.ProductInfoRepository;
import com.ccloud.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/12 23:03
 * @description：商品服务类
 * @modified By：
 */
@Service
// @CacheConfig(cacheNames = "product")
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository repository;

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public List<ProductInfo> findList(List<String> productIdList) {
        return repository.findByProductIdIn(productIdList);
    }

}
