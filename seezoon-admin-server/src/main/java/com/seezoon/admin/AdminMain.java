package com.seezoon.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seezoon.dao.modules.sys.SysParamDao;
import com.seezoon.dao.modules.sys.entity.SysParam;

/**
 * 程序入口
 *
 * @author hdf
 */
@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)
@RestController
public class AdminMain {

    @Autowired
    private SysParamDao sysParamDao;
    @Value("${a}")
    private String a;

    public static void main(String[] args) {
        SpringApplication.run(AdminMain.class, args);
    }

    @RequestMapping("/1")
    public SysParam t() {
        System.out.println(a);
        return sysParamDao.selectByPrimaryKey(1);
    }
}
