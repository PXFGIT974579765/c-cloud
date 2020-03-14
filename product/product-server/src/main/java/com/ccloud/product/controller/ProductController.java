package com.ccloud.product.controller;

import com.ccloud.product.dataobject.ProductCategory;
import com.ccloud.product.dataobject.ProductInfo;
import com.ccloud.product.service.CategoryService;
import com.ccloud.product.service.ProductService;
import com.ccloud.product.utils.ResultVOUtil;
import com.ccloud.product.vo.ProductInfoVo;
import com.ccloud.product.vo.ProductVo;
import com.ccloud.product.vo.ResultVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/12 22:49
 * @description： 商品控制层
 * @modified By：
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;


    /**
     * 功能描述:
     * 获取所有商品信息，本项目里暂且不考虑分页
     * 此处可以使用缓存，等更新商品时清除缓存即可
     * @Author: 腾云先生
     * @Date: 2020/03/12 22:55
     */
    @GetMapping("list")
    //@Cacheable(cacheNames = "product", key = "123")
    public ResultVo list() {
        // 查询所有上架的商品
        List<ProductInfo> productInfoList = productService.findUpAll();


        //查询类目列表
        List<Integer> categoryTypeList = productInfoList.stream().map(ProductInfo::getCategoryType).collect(Collectors.toList());
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);

        //数据拼装
        List<ProductVo> productVoList = new ArrayList<>();
        for (ProductCategory productCategory : productCategoryList) {
            ProductVo productVo = new ProductVo();
            productVo.setCategoryType(productCategory.getCategoryType());
            productVo.setCategoryName(productCategory.getCategoryName());

            List<ProductInfoVo> productInfoVoList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    ProductInfoVo productInfoVo = new ProductInfoVo();
                    BeanUtils.copyProperties(productInfo, productInfoVo);
                    productInfoVoList.add(productInfoVo);
                }
            }
            productVo.setProductInfoVoList(productInfoVoList);
            productVoList.add(productVo);
        }
        return ResultVOUtil.success(productVoList);
    }

    /**
     * 功能描述:
     * 根据商品id获取商品列表（给订单服务用）
     * @Author: 腾云先生
     * @Date: 2020/03/12 22:50
     */
    @PostMapping("/listForOrder")
    public List<ProductInfo> listForOrder(@RequestBody List<String> productIdList) {
        return productService.findList(productIdList);
    }

}
