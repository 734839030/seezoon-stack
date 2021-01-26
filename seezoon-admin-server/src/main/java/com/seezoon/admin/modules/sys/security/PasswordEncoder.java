package com.seezoon.admin.modules.sys.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author hdf
 */
public class PasswordEncoder {

    public static String encode(String password) {
        if (StringUtils.isEmpty(password)) {
            return null;
        } else {
            return new BCryptPasswordEncoder().encode(password);
        }
    }
}
