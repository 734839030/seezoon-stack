package com.seezoon.admin.modules.sys.controller;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public Result<SysParam> queryById(@NotNull Integer id) {
        SysParam sysParam = sysParamService.findById(id);
        return Result.ok(sysParam);
    }

    @PostMapping(value = "/save",
        consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public Result save(@Validated @RequestBody SysParam sysParam) {
        sysParamService.save(sysParam);
        return Result.SUCCESS;
    }
}
