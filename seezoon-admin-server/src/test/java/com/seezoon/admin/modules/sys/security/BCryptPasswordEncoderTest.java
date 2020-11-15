package com.seezoon.admin.modules.sys.security;

import org.junit.Assert;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author hdf
 */
public class BCryptPasswordEncoderTest {

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);

    @RepeatedTest(10)
    public void encode() {
        String encode = bCryptPasswordEncoder.encode("123");
        System.out.println(encode);
        Assert.assertTrue(bCryptPasswordEncoder.matches("123", encode));
    }
}
