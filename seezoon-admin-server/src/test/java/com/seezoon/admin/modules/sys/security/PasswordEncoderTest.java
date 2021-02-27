package com.seezoon.admin.modules.sys.security;

import org.junit.jupiter.api.Test;

/**
 * @author hdf
 */
class PasswordEncoderTest {

    @Test
    void encode() {
        System.out.println(AdminPasswordEncoder.encode("123456"));
    }
}