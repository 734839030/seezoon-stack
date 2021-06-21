package com.seezoon.user.modules.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * 默认返回403的够用，复写的原因是为了打印accessDenied时候的日志信息便于定位
 *
 * @author hdf
 */
@Slf4j
public class UserAccessDeniedHandler extends AccessDeniedHandlerImpl {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
        AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("User Access Denied:{}", accessDeniedException.getMessage());
        super.handle(request, response, accessDeniedException);
    }
}
