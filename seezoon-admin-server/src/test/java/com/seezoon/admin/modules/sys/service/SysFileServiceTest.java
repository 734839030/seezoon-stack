package com.seezoon.admin.modules.sys.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.seezoon.admin.BaseSpringBootTest;

/**
 * @author hdf
 */
class SysFileServiceTest extends BaseSpringBootTest {
    @Autowired
    SysFileService sysFileService;

    @Test
    void info() {
        sysFileService.info("/2020/1/11/111.jpg,/2020/1/11/111.jpg,/2020/1/11/22.jpg", true);
    }
}