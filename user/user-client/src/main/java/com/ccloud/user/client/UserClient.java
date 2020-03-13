package com.ccloud.user.client;

import com.ccloud.user.params.LoginParam;
import com.ccloud.user.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/13 20:46
 * @description：user服务对外暴露的客户端接口
 * @modified By：
 */
@FeignClient(
        name = "user"
        ,fallback = UserClient.UserClientFallback.class

)
public interface UserClient {

    @PostMapping("/login")
    public ResultVo login(@RequestBody LoginParam loginParam);


    @Component
    @Slf4j
    static class UserClientFallback implements UserClient {


        @Override
        public ResultVo login(LoginParam loginParam) {
            log.info("用户中心未正常工作");
            return null;
        }

    }
}
