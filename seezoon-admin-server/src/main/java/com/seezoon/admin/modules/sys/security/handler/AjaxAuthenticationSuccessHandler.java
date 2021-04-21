package com.seezoon.admin.modules.sys.security.handler;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.seezoon.admin.modules.sys.eventbus.AdminEventBus;
import com.seezoon.admin.modules.sys.security.SecurityUtils;
import com.seezoon.admin.modules.sys.security.constant.LoginResult;
import com.seezoon.admin.modules.sys.security.listener.LoginEventListener;
import com.seezoon.framework.api.Result;
import com.seezoon.framework.utils.IpUtil;
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
        String username = authentication.getName();
        LoginEventListener.LoginResultMsg loginResultMsg = new LoginEventListener.LoginResultMsg(username, new Date(),
            IpUtil.getRemoteIp(request), request.getHeader("User-Agent"));
        loginResultMsg.setResult(LoginResult.SUCCESS);
        loginResultMsg.setUserId(SecurityUtils.getUserId());
        AdminEventBus.publish(loginResultMsg);
        super.sendRespone(response, Result.SUCCESS);
    }

}
