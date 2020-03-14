package com.ccloud.user.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/12 23:59
 * @description：用户信息entity
 * @modified By：
 */
@Data
@Entity
public class UserInfo {

    @Id
    private String id;

    /** 用户姓名 */
    private String username;

    private String password;

    private String phone;

    /** 用户余额 */
    private BigDecimal deposit;

}
