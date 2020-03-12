package com.ccloud.product.client;

import com.ccloud.product.io.ProductInfoOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/12 22:38
 * @description： 商品服务对外暴露的feign client
 * @modified By：
 */
@FeignClient(name = "product"
        , fallback = ProductClient.ProductClientFallback.class
)
public interface ProductClient {

    @PostMapping("/product/listForOrder")
    List<ProductInfoOutput> listForOrder(@RequestBody List<String> productIdList);

    @Component
    @Slf4j
    static class ProductClientFallback implements ProductClient {

        @Override
        public List<ProductInfoOutput> listForOrder(List<String> productIdList) {
            log.error("服务降级啦");
            return null;
        }

    }
}
