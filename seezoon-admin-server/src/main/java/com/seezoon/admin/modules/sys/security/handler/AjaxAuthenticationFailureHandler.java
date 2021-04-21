package com.seezoon.admin.modules.sys.security.handler;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.seezoon.admin.framework.error.AdminCodeMsgBundle;
import com.seezoon.admin.modules.sys.eventbus.AdminEventBus;
import com.seezoon.admin.modules.sys.security.constant.LockType;
import com.seezoon.admin.modules.sys.security.constant.LoginResult;
import com.seezoon.admin.modules.sys.security.listener.LoginEventListener;
import com.seezoon.framework.api.Result;
import com.seezoon.framework.utils.IpUtil;
import com.seezoon.framework.web.respone.AbstractJsonResponeHandler;

/**
 * @author hdf
 */
public class AjaxAuthenticationFailureHandler extends AbstractJsonResponeHandler
    implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException exception) throws IOException {
        Result result = null;

        LoginEventListener.LoginResultMsg loginResultMsg = new LoginEventListener.LoginResultMsg(
            this.obtainUsername(request), new Date(), IpUtil.getRemoteIp(request), request.getHeader("User-Agent"));

        Throwable cause = exception.getCause() == null ? exception : exception.getCause();

        if (cause instanceof UsernameNotFoundException) {
            result = Result.error(AdminCodeMsgBundle.USERNAME_NOT_FOUND);
            loginResultMsg.setResult(LoginResult.USERNAME_NOT_FOUND);
        } else if (cause instanceof BadCredentialsException) {
            result = Result.error(AdminCodeMsgBundle.BAD_CREDENTIALS);
            loginResultMsg.setResult(LoginResult.PASSWD_ERROR);
        } else if (cause instanceof LockedException) {
            if (cause.getMessage().equals(LockType.USERNAME.name())) {
                result = Result.error(AdminCodeMsgBundle.USERNAME_LOCKED);
                loginResultMsg.setResult(LoginResult.USERNAME_LOCKED);
            } else {
                result = Result.error(AdminCodeMsgBundle.IP_LOCKED);
                loginResultMsg.setResult(LoginResult.IP_LOCKED);
            }
        } else if (cause instanceof DisabledException) {
            result = Result.error(AdminCodeMsgBundle.DISABLED);
            loginResultMsg.setResult(LoginResult.DISABLED);
        } else {
            result = Result.error(AdminCodeMsgBundle.UNKOWN_LOGIN, exception.getMessage());
            loginResultMsg.setResult(LoginResult.UNKOWN);
        }
        loginResultMsg.setUserId(LoginResult.DEFAULT_USER_ID);
        AdminEventBus.publish(loginResultMsg);
        super.sendRespone(response, result);
    }

    private String obtainUsername(HttpServletRequest request) {
        return request.getParameter("username");
    }
}
