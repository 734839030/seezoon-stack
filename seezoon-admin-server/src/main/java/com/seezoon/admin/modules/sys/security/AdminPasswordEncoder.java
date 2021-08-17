package com.seezoon.admin.modules.sys.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author hdf
 */
public class AdminPasswordEncoder {

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
        return new AdminBCryptPasswordEncoder();
    }

    public static class AdminBCryptPasswordEncoder extends BCryptPasswordEncoder {
        @Override
        public String encode(CharSequence rawPassword) {
            if (StringUtils.isEmpty(rawPassword)) {
                return null;
            }
            return super.encode(rawPassword);
        }

        @Override
        public boolean matches(CharSequence rawPassword, String encodedPassword) {
            if (StringUtils.isEmpty(rawPassword) && StringUtils.isEmpty(encodedPassword)) {
                return true;
            }
            return super.matches(rawPassword, encodedPassword);
        }
    }
}
