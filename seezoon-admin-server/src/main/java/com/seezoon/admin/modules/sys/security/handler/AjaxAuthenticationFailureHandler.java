package com.seezoon.admin.modules.sys.security.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.seezoon.admin.framework.error.AdminCodeMsgBundle;
import com.seezoon.framework.api.Result;
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
        if (exception instanceof UsernameNotFoundException) {
            result = Result.error(AdminCodeMsgBundle.USERNAME_NOT_FOUND);
        } else if (exception instanceof BadCredentialsException) {
            result = Result.error(AdminCodeMsgBundle.BAD_CREDENTIALS);
        } else if (exception instanceof LockedException) {
            result = Result.error(AdminCodeMsgBundle.LOCKED);
        } else if (exception instanceof DisabledException) {
            result = Result.error(AdminCodeMsgBundle.DISABLED);
        } else {
            result = Result.error(AdminCodeMsgBundle.UNKOWN_LOGIN, exception.getMessage());
        }
        super.sendRespone(response, result);
    }
}
