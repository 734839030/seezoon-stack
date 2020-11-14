package com.seezoon.admin.modules.sys.security.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.seezoon.framework.api.Result;
import com.seezoon.framework.web.respone.AbstractJsonResponeHandler;

/**
 * 前后端分离场景下的成功处理
 *
 * @author hdf
 */
public class AjaxAuthenticationSuccessHandler extends AbstractJsonResponeHandler
    implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException {
        super.sendRespone(response, Result.SUCCESS);
    }
}
