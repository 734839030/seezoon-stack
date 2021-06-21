package com.seezoon.admin.modules.sys.controller;

import org.junit.jupiter.api.Test;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author hdf
 */
class UserControllerTest {

    @Test
    public void testUrlBuilder() {
        UriComponents build = UriComponentsBuilder.fromUriString("http://localhost:3100/api//doc.html").build();
        System.out.println(build.getPath());

    }
}