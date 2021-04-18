package com.seezoon.admin.modules.sys.controller;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.github.pagehelper.PageSerializable;
import com.seezoon.admin.modules.sys.service.SysLoginLogService;
import com.seezoon.dao.modules.sys.entity.SysLoginLog;
import com.seezoon.dao.modules.sys.entity.SysLoginLogCondition;
import com.seezoon.framework.api.DefaultCodeMsgBundle;
import com.seezoon.framework.api.Result;
import com.seezoon.framework.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

/**
 * 登录日志
 *
 * @author seezoon-generator 2021年4月18日 上午1:30:18
 */
@Api(tags = "登录日志")
@RestController
@RequestMapping("/sys/login_log")
@RequiredArgsConstructor
public class SysLoginLogController extends BaseController {

    private final SysLoginLogService sysLoginLogService;

    @ApiOperation(value = "主键查询")
    @PreAuthorize("hasAuthority('sys:login_log:query')")
    @GetMapping("/query/{id}")
    public Result<SysLoginLog> query(@PathVariable Integer id) {
        SysLoginLog sysLoginLog = sysLoginLogService.find(id);
        return Result.ok(sysLoginLog);
    }

    @ApiOperation(value = "分页查询")
    @PreAuthorize("hasAuthority('sys:login_log:query')")
    @PostMapping("/query")
    public Result<PageSerializable<SysLoginLog>> query(@Valid @RequestBody SysLoginLogCondition condition) {
        PageSerializable<SysLoginLog> pageSerializable =
            sysLoginLogService.find(condition, condition.getPage(), condition.getPageSize());
        return Result.ok(pageSerializable);
    }

    @ApiOperation(value = "删除")
    @PreAuthorize("hasAuthority('sys:login_log:delete')")
    @PostMapping(value = "/delete")
    public Result delete(@RequestParam Integer id) {
        int count = sysLoginLogService.delete(id);
        return count == 1 ? Result.SUCCESS : Result.error(DefaultCodeMsgBundle.DELETE_ERROR, count);
    }

}
