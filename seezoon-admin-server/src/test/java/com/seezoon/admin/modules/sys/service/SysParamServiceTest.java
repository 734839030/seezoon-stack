package com.seezoon.admin.modules.sys.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.seezoon.admin.BaseSpringBootTest;

import lombok.RequiredArgsConstructor;

/**
 * @author hdf
 */

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class SysParamServiceTest extends BaseSpringBootTest {

    private final SysParamService sysParamService;

    @BeforeAll
    public static void beforeAll() {}

    @Test
    void findById() {}

    @Test
    void findByCondition() {}

    @Test
    void findByPage() {}

    @Test
    void testFindByPage() {}

    @Test
    void save() {}

    @Test
    void updateSelective() {}

    @Test
    void update() {}

    @Test
    void delete() {}
}