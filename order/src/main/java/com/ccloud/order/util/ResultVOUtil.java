package com.ccloud.order.util;


import com.ccloud.order.vo.ResultVo;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/12 00:08
 * @description：最外层返回对象的包装工具
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
}
