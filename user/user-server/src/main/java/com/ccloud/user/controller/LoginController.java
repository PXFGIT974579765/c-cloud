package com.ccloud.user.controller;

import com.ccloud.user.dataobject.UserInfo;
import com.ccloud.user.enums.ResultEnum;
import com.ccloud.user.params.LoginParam;
import com.ccloud.user.service.UserService;
import com.ccloud.user.util.KeyUtil;
import com.ccloud.user.util.MD5Util;
import com.ccloud.user.util.ResultVOUtil;
import com.ccloud.user.vo.ResultVo;
import com.ccloud.user.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/13 00:05
 * @description：用户控制层
 * @modified By：
 */
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 买家登录
     * @param loginParam
     * @param response
     * @return
     */
    @PostMapping("/login")
    public ResultVo login(@RequestBody LoginParam loginParam, HttpServletResponse response) {

        if (StringUtils.isEmpty(loginParam.getPhone())) {
            return ResultVOUtil.error(ResultEnum.PHONE_EMPTY);
        } else if (StringUtils.isEmpty(loginParam.getPassword())) {
            return ResultVOUtil.error(ResultEnum.PWD_EMPTY);
        }

        // 1、phone和数据库里的数据是否匹配
        UserInfo userInfo = userService.findByPhone(loginParam.getPhone());
        if (userInfo == null
            || !MD5Util.encrypt(loginParam.getPassword()).equals(userInfo.getPassword())) {
            return ResultVOUtil.error(ResultEnum.LOGIN_FAIL);
        }

        UserVo userVo = UserVo.adapt(userInfo);
        userVo.setToken(KeyUtil.uuid32());

        return ResultVOUtil.success(userVo);
    }
}
