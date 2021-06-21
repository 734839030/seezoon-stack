package com.seezoon.user.modules.security;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author hdf
 */
public class UserPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return null;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return true;
    }
}
