package com.seezoon.admin.modules.sys.controller;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seezoon.admin.modules.sys.service.SysParamService;
import com.seezoon.dao.modules.sys.entity.SysParam;
import com.seezoon.framework.api.Result;
import com.seezoon.framework.web.BaseController;

import lombok.RequiredArgsConstructor;

/**
 * @author hdf
 */
@RestController
@RequestMapping("/sys")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysParamController extends BaseController {

    private final SysParamService sysParamService;

    @GetMapping("/queryById")
    public Result<SysParam> queryById(@NotNull Integer id, Date date) {
        SysParam sysParam = sysParamService.findById(id);
        return Result.ok(sysParam);
    }
}
