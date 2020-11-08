package com.seezoon.admin.modules.sys.controller;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.github.pagehelper.PageInfo;
import com.seezoon.admin.modules.sys.service.SysParamService;
import com.seezoon.dao.modules.sys.entity.SysParam;
import com.seezoon.dao.modules.sys.entity.SysParamCondition;
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

    @PostMapping("/queryByPage")
    public Result<PageInfo<SysParam>> queryByPage(SysParamCondition condition) {
        PageInfo<SysParam> pageInfo =
            sysParamService.findByPage(condition, condition.getPage(), condition.getPageSize());
        return Result.ok(pageInfo);
    }

    @PostMapping(value = "/save")
    public Result save(@Validated @RequestBody SysParam sysParam) {
        sysParamService.save(sysParam);
        return Result.SUCCESS;
    }

    @PostMapping(value = "/update")
    public Result update(@Validated @RequestBody SysParam sysParam) {
        sysParamService.update(sysParam);
        return Result.SUCCESS;
    }

    @PostMapping(value = "/delete")
    public Result delete(@NotNull Integer id) {
        sysParamService.delete(id);
        return Result.SUCCESS;
    }
}
