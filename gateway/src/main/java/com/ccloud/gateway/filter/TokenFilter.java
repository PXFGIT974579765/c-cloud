package com.ccloud.gateway.filter;

import com.ccloud.user.client.UserClient;
import com.ccloud.user.vo.ResultVo;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/13 22:29
 * @description：token校验类
 * @modified By：
 */
@Component
@Slf4j
public class TokenFilter extends ZuulFilter {

    @Autowired
    private UserClient userClient;

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        if(!requestContext.sendZuulResponse()){
            return false;
        }

        HttpServletRequest request = requestContext.getRequest();
        if (request.getRequestURI().contains("/user/login") || request.getRequestURI().contains("/product/list")) {
            return false;
        }
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        ResultVo result = userClient.auth();
        if(!result.getCode().equals(0)) {
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }
        return null;
    }
}