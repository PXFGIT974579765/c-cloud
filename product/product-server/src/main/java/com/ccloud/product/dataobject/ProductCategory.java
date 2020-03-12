package com.ccloud.product.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/12 00:13
 * @description：商品类目信息entity
 * @modified By：
 */
@Entity
@Data
@DynamicUpdate
public class ProductCategory {

    /**
     * 类目id
     **/
    @Id
    @GeneratedValue
    private Integer categoryId;

    /**
     * 类目名字
     **/
    private String categoryName;

    /**
     * 类目编号
     **/
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

    public ProductCategory() {
    }

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
}
