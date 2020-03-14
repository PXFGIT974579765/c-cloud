package com.ccloud.gateway.config;

import com.ccloud.gateway.intercepter.FeignBasicAuthRequestInterceptor;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/14 20:30
 * @description：feign配置全局
 * @modified By：
 */
@Configuration
public class FeignSupportConfig {
    /**
     * feign请求拦截器
     *
     * @return
     */
    @Bean
    public RequestInterceptor requestInterceptor(){
        return new FeignBasicAuthRequestInterceptor();
    }
}
