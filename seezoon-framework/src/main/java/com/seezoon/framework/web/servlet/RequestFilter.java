package com.seezoon.framework.web.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.seezoon.framework.log.MdcHelper;

import lombok.extern.slf4j.Slf4j;

/**
 * 框架中做请求的拦截处理
 *
 * 未添加{@code  @WebFilter}，不让@ServletComponentScan 扫描，采用spring 加载servlet filter，可以调整顺序，
 *
 * 比如可以早于spring security filter的顺序
 *
 *
 * @author hdf
 */
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
