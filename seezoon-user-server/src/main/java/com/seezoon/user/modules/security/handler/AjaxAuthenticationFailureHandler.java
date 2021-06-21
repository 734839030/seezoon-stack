package com.seezoon.user.modules.security.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.seezoon.framework.api.Result;
import com.seezoon.framework.web.respone.AbstractJsonResponeHandler;
import com.seezoon.user.framework.error.UserCodeMsgBundle;

/**
 * @author hdf
 */
public class AjaxAuthenticationFailureHandler extends AbstractJsonResponeHandler
    implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException exception) throws IOException {
        Result result = null;

        Throwable cause = exception.getCause() == null ? exception : exception.getCause();

        if (cause instanceof UsernameNotFoundException) {
            result = Result.error(UserCodeMsgBundle.USERNAME_NOT_FOUND);
        } else if (cause instanceof BadCredentialsException) {
            result = Result.error(UserCodeMsgBundle.BAD_CREDENTIALS);
        } else if (cause instanceof LockedException) {
            result = Result.error(UserCodeMsgBundle.USERNAME_LOCKED);
        } else if (cause instanceof DisabledException) {
            result = Result.error(UserCodeMsgBundle.DISABLED);
        } else {
            result = Result.error(UserCodeMsgBundle.UNKOWN_LOGIN, exception.getMessage());
        }
        super.sendRespone(response, result);
    }

    private String obtainUsername(HttpServletRequest request) {
        return request.getParameter("username");
    }
}
