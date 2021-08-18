package com.seezoon.admin.modules.sys.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author hdf
 */
public class AdminPasswordEncoder {

    /**
     * 适合低三方登录的无密码传入
     */
    public static final String NONE_PASSWORD = AdminPasswordEncoder.encode("none");

    public static String encode(String password) {
        if (StringUtils.isEmpty(password)) {
            return null;
        } else {
            return new BCryptPasswordEncoder().encode(password);
        }
    }

    public static boolean matches(CharSequence rawPassword, String encodedPassword) {
        return getEncoder().matches(rawPassword, encodedPassword);
    }

    public static PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

}
