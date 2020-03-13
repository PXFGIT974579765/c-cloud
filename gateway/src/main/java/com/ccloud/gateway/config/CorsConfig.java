package com.ccloud.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/13 21:49
 * @description：跨域配置
 * @modified By：
 */
@Configuration
public class CorsConfig {

    /**
     * 功能描述:
     * 文档：跨域.note
     * 链接：http://note.youdao.com/noteshare?id=7fb2a7f7976235619e10445844dbbf0c&sub=83C7B8025A0D4F79965956EA2BBE17C4
     * 1、在请求中需要附加一个额外的 Origin 头部，其中包含请求页面的源信息（协议、域名和端口），以便服务器根据这个头部信息来决定是否给予响应。例如：Origin: http://www.laixiangran.cn
     * 2、如果服务器认为这个请求可以接受，就在 Access-Control-Allow-Origin 头部中回发相同的源信息（如果是公共资源，可以回发 * ）。例如：Access-Control-Allow-Origin：http://www.laixiangran.cn
     * 3、没有这个头部或者有这个头部但源信息不匹配，浏览器就会驳回请求。正常情况下，浏览器会处理请求。注意，请求和响应都不包含 cookie 信息。
     * 4、如果需要包含 cookie 信息，ajax 请求需要设置 xhr 的属性 withCredentials 为 true，服务器需要设置响应头部 Access-Control-Allow-Credentials: true。
     * @Author: 腾云先生
     * @Date: 2020/03/13 21:52
     */
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();

        // 设置是否支持cookie跨域
        config.setAllowCredentials(true);

        // 设置允许的原始域  比如： http://www.a.com   http://www.b.com
        config.setAllowedOrigins(Arrays.asList("*"));

        //设置允许的头
        config.setAllowedHeaders(Arrays.asList("*"));

        // 设置允许跨域的方法，  GET   POST
        config.setAllowedMethods(Arrays.asList("*"));

        // 缓存时间，在这个时间段里，对于相同的跨域请求就不在进行跨域检查，主要针对复杂请求options预检命令
        config.setMaxAge(300L);

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
