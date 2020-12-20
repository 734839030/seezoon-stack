package com.seezoon.admin.modules.sys.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.seezoon.admin.BaseSpringBootTest;
import com.seezoon.dao.modules.sys.entity.SysParam;

/**
 * @author hdf
 */

class SysParamServiceTest extends BaseSpringBootTest {

    @Autowired
    private SysParamService sysParamService;

    @Test
    void findByParamKey() {
        for (int i = 0; i < 2; i++) {
            SysParam sysParam = sysParamService.findByParamKey("key1");
            System.out.println(sysParam.getName());
        }
    }

    @Test
    void save() {
        for (int i = 0; i < 200; i++) {
            SysParam sysParam = sysParamService.findByParamKey("key1");
            sysParam.setName("key-n-" + i);
            sysParam.setParamKey("key-k" + i);
            sysParamService.save(sysParam);
        }
    }
}