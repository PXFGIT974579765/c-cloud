package com.ccloud.user.params;

import lombok.Data;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/13 00:11
 * @description：登录表单
 * @modified By：
 */
@Data
public class LoginParam {

    private String phone;

    private String password;
}
