package com.ccloud.product.dataobject;

import com.ccloud.product.enums.ProductStatusEnum;
import com.ccloud.product.utils.EnumUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/12 00:16
 * @description：商品信息entity
 * @modified By：
 */
@Entity
@Data
@DynamicUpdate
public class ProductInfo {

    private static final long serialVersionUID = 1688412074231146844L;

    @Id
    private String productId;

    /**
     * 名称.
     */
    private String productName;

    /**
     * 价格.
     */
    private BigDecimal productPrice;

    /**
     * 库存.
     */
    private Integer productStock;

    /**
     * 小图.
     */
    private String productIcon;

    /**
     * 描述.
     */
    private String productDescription;

    /**
     * 状态 0正常 1下架.
     */
    private Integer productStatus = ProductStatusEnum.UP.getCode();

    /**
     * 类目编号.
     */
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

    @JsonIgnore
    public ProductStatusEnum getProductStatusEnum() {
        return EnumUtil.getByCode(productStatus, ProductStatusEnum.class);
    }
}
