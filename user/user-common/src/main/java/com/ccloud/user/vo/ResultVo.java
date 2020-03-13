package com.ccloud.user.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/13 00:14
 * @description：请求返回的最外层对象
 * @modified By：
 */
@Data
public class ResultVo<T> implements Serializable {

    private static final long serialVersionUID = 1785001069726554761L;
    /**
     * 错误码.
     */
    private Integer code;

    /**
     * 错误信息.
     */
    private String message;

    /**
     * 具体内容.
     */
    private T data;
}