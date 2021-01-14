package com.seezoon.admin.modules.sys.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.seezoon.admin.BaseSpringBootTest;
import com.seezoon.dao.framework.dto.Tree;

/**
 * @author hdf
 */
class SysDeptServiceTest extends BaseSpringBootTest {

    @Autowired
    private SysDeptService sysDeptService;

    @Test
    void updateSelective() {}

    @Test
    void findTree() {
        List<Tree> trees = sysDeptService.findTree(0, true);
        System.out.println(JSON.toJSONString(trees));
    }
}