package com.seezoon.generator.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.val;

/**
 * @author hdf
 */
@SpringBootTest
class TableInfoDaoTest {

    @Autowired
    private TableInfoDao tableInfoDao;

    @Test
    void findTable() {
        val table = tableInfoDao.findTable(null);
        table.forEach((v) -> {
            System.out.println(v.getComment());
        });
    }

    @Test
    void findColumnByTableName() {
        val sysParamColumns = tableInfoDao.findColumnByTableName("sys_param");
        System.out.println(sysParamColumns.size());
    }

    @Test
    void findPkType() {
        System.out.println(tableInfoDao.findPkType("sys_param"));;
    }
}