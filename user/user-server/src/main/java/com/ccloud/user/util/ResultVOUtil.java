package com.ccloud.user.util;

import com.ccloud.user.enums.ResultEnum;
import com.ccloud.user.vo.ResultVo;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/13 00:15
 * @description：最外层返回对象封装工具类
 * @modified By：
 */
public class ResultVOUtil {

    public static ResultVo success(Object object) {
        ResultVo resultVo = new ResultVo();
        resultVo.setData(object);
        resultVo.setCode(0);
        resultVo.setMessage("成功");
        return resultVo;
    }

    public static ResultVo success() {
        return success(null);
    }

    public static ResultVo error(Integer code, String msg) {
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(code);
        resultVo.setMessage(msg);
        return resultVo;
    }

    public static ResultVo error(ResultEnum resultEnum) {
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(resultEnum.getCode());
        resultVo.setMessage(resultEnum.getMessage());
        return resultVo;
    }
}
