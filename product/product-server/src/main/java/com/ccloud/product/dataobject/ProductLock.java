package com.ccloud.product.dataobject;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/12 00:16
 * @description：商品信息entity
 * @modified By：
 */
@Entity
@Data
@DynamicUpdate
@Builder
public class ProductLock {

    private static final long serialVersionUID = 1688412074231146844L;

    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 买家电话.
     */
    private String phone;

    /**
     * 商品id.
     */
    private String productId;

}
