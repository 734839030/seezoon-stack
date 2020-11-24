package com.seezoon.admin.modules.sys.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.github.pagehelper.PageInfo;
import com.seezoon.admin.modules.sys.service.SysParamService;
import com.seezoon.dao.modules.sys.entity.SysParam;
import com.seezoon.dao.modules.sys.entity.SysParamCondition;
import com.seezoon.framework.api.Result;
import com.seezoon.framework.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

/**
 * @author hdf
 */
@Api(tags = "系统参数")
@RestController
@RequestMapping("/sys")
@RequiredArgsConstructor
public class SysParamController extends BaseController {

    private final SysParamService sysParamService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "主键查询")
    @GetMapping("/queryById")
    public Result<SysParam> queryById(@RequestParam Integer id, HttpSession session) {
        int maxInactiveInterval = session.getMaxInactiveInterval();
        SysParam sysParam = sysParamService.find(id);
        return Result.ok(sysParam);
    }

    @ApiOperation(value = "分页查询")
    @PostMapping("/queryByPage")
    public Result<PageInfo<SysParam>> queryByPage(SysParamCondition condition) {
        PageInfo<SysParam> pageInfo = sysParamService.find(condition, condition.getPage(), condition.getPageSize());
        return Result.ok(pageInfo);
    }

    @ApiOperation(value = "保存")
    @PostMapping(value = "/save")
    public Result save(@Valid @RequestBody SysParam sysParam) {
        sysParamService.save(sysParam);
        return Result.SUCCESS;
    }

    @ApiOperation(value = "更新")
    @PostMapping(value = "/update")
    public Result update(@Valid @RequestBody SysParam sysParam) {
        sysParamService.updateSelective(sysParam);
        return Result.SUCCESS;
    }

    @ApiOperation(value = "删除")
    @PostMapping(value = "/delete")
    public Result delete(@RequestParam Integer id) {
        sysParamService.delete(id);
        return Result.SUCCESS;
    }
}
