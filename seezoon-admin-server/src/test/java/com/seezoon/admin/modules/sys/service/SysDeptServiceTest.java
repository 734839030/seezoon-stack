package com.seezoon.admin.modules.sys.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.seezoon.admin.BaseSpringBootTest;

/**
 * @author hdf
 */
class SysDeptServiceTest extends BaseSpringBootTest {

    @Autowired
    private SysDeptService sysDeptService;

    @Test
    void findParentAndChildrenIds() {
        System.out.println(sysDeptService.findParentAndChildrenIds(2));
    }
}