package com.seezoon.framework.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import com.alibaba.fastjson.JSON;
import com.seezoon.framework.log.MdcHelper;

import lombok.extern.slf4j.Slf4j;

/**
 * 框架中做请求的拦截处理
 *
 * @author hdf
 */
@WebFilter
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class RequestFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        if (log.isDebugEnabled()) {
            // form 参数打印
            Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
            if (!parameterMap.isEmpty()) {
                log.debug("Request Params=>{}", JSON.toJSONString(parameterMap));
            }
        }
        // 添加调用链响应头,因为spring 会在filter中结束respone 所以提前加上
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        httpServletResponse.setHeader(MdcHelper.HTTP_HEADER, MdcHelper.peek());
        chain.doFilter(request, response);
    }
}
