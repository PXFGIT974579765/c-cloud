package com.ccloud.order.exception;

import com.ccloud.order.enums.ResultEnum;
import lombok.Data;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/14 13:46
 * @description：自定义异常
 * @modified By：
 */
@Data
public class AppException extends RuntimeException {

    private Integer code;

    public AppException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public AppException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
