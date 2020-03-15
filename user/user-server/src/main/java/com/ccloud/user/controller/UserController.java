package com.ccloud.user.controller;

import com.ccloud.user.constant.HeaderConstant;
import com.ccloud.user.constant.RedisConstant;
import com.ccloud.user.dataobject.UserInfo;
import com.ccloud.user.enums.ResultEnum;
import com.ccloud.user.params.LoginParam;
import com.ccloud.user.service.UserService;
import com.ccloud.user.util.HeaderUtil;
import com.ccloud.user.util.KeyUtil;
import com.ccloud.user.util.MD5Util;
import com.ccloud.user.util.ResultVOUtil;
import com.ccloud.user.vo.ResultVo;
import com.ccloud.user.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/13 00:05
 * @description：用户控制层
 * @modified By：
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

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

        String redisToken = (String) redisTemplate.opsForValue().get(String.format(RedisConstant.UID_TEMPLATE, userVo.getId()));

        // 从redis删除uid 和token
        redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.UID_TEMPLATE, userVo.getId()));
        redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_TEMPLATE, redisToken));

        String newToken = KeyUtil.uuid32();

        // 3、将用户信息存入 redis, token_s% = User();
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_TEMPLATE, newToken),
                userVo, RedisConstant.expire, TimeUnit.SECONDS);

        // 4、将 token 存入 redis
        redisTemplate.opsForValue().set(String.format(RedisConstant.UID_TEMPLATE, userVo.getId()), newToken, RedisConstant.expire, TimeUnit.SECONDS);

        // 5、登录成功将 token 放入header里
        HeaderUtil.set(response, HeaderConstant.TOKEN, newToken);

        userVo.setToken(newToken);

        return ResultVOUtil.success(userVo);
    }

    @RequestMapping("/auth")
    public ResultVo auth(HttpServletRequest request) {
        String token = HeaderUtil.get(request, HeaderConstant.TOKEN);
        if(StringUtils.isEmpty(token)){
            return ResultVOUtil.error(ResultEnum.TOKEN_ERROR);
        }

        UserVo user = (UserVo) redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_TEMPLATE,token));
        if(user == null) {
            return ResultVOUtil.error(ResultEnum.TOKEN_ERROR);
        }

        return ResultVOUtil.success();
    }
}
