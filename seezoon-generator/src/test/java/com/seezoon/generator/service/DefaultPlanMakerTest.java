package com.seezoon.generator.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSON;
import com.seezoon.generator.dto.plan.TablePlan;

/**
 * @author hdf
 */
@SpringBootTest
class DefaultPlanMakerTest {

    @Autowired
    private SystemTablePlanMaker systemTablePlanMaker;

    @Test
    void getPlan() {
        final TablePlan tablePlan = systemTablePlanMaker.getPlan("sys_param");
        System.out.println(JSON.toJSONString(tablePlan));
    }
}