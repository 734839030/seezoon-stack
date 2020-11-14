package com.seezoon.admin.modules.sys.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.seezoon.framework.api.Result;
import com.seezoon.framework.web.respone.AbstractJsonResponeHandler;

/**
 * @author hdf
 */
public class AjaxLogoutSuccessHandler extends AbstractJsonResponeHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
        throws IOException, ServletException {
        super.sendRespone(response, Result.SUCCESS);
    }
}
