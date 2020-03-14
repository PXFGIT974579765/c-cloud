package com.ccloud.order.handler;


import com.ccloud.order.exception.AppException;
import com.ccloud.order.exception.ParamException;
import com.ccloud.order.util.ResultVOUtil;
import com.ccloud.order.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ：腾云先生
 * @date ：Created in 2019/05/15 15:53
 * @description：异常捕获
 * @modified By：
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandle {


    @ExceptionHandler(value = AppException.class)
    @ResponseBody
    public ResultVo handle(AppException e) {
        log.error("【系统异常】{}", e);
        return ResultVOUtil.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = ParamException.class)
    @ResponseBody
    public ResultVo handle(ParamException e) {
        log.error("【系统异常】{}", e);
        return ResultVOUtil.error(-1, e.getMessage());
    }
}
