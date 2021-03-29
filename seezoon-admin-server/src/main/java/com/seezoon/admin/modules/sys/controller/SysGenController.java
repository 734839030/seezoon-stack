package com.seezoon.admin.modules.sys.controller;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.github.pagehelper.PageSerializable;
import com.seezoon.admin.modules.sys.service.SysGenService;
import com.seezoon.dao.modules.sys.entity.SysGen;
import com.seezoon.dao.modules.sys.entity.SysGenCondition;
import com.seezoon.framework.api.DefaultCodeMsgBundle;
import com.seezoon.framework.api.Result;
import com.seezoon.framework.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

/**
 * 代码生成
 * @author seezoon-generator 2021年3月29日 下午11:27:05
 */
@Api(tags = "代码生成")
@RestController
@RequestMapping("/sys/gen")
@RequiredArgsConstructor
public class SysGenController extends BaseController {

    private final SysGenService sysGenService;

    @ApiOperation(value = "主键查询")
    @PreAuthorize("hasAuthority('sys:gen:query')")
    @GetMapping("/query/{id}")
    public Result<SysGen> query(@PathVariable Integer id) {
        SysGen sysGen = sysGenService.find(id);
        return Result.ok(sysGen);
    }

    @ApiOperation(value = "分页查询")
    @PreAuthorize("hasAuthority('sys:gen:query')")
    @PostMapping("/query")
    public Result<PageSerializable<SysGen>> query(@Valid @RequestBody SysGenCondition condition) {
        PageSerializable<SysGen> pageSerializable = sysGenService.find(condition, condition.getPage(), condition.getPageSize());
        return Result.ok(pageSerializable);
    }

    @ApiOperation(value = "保存")
    @PreAuthorize("hasAuthority('sys:gen:save')")
    @PostMapping(value = "/save")
    public Result save(@Valid @RequestBody SysGen sysGen) {
        int count = sysGenService.save(sysGen);
        return count == 1 ? Result.SUCCESS : Result.error(DefaultCodeMsgBundle.SAVE_ERROR, count);
    }

    @ApiOperation(value = "更新")
    @PreAuthorize("hasAuthority('sys:gen:update')")
    @PostMapping(value = "/update")
    public Result update(@Valid @RequestBody SysGen sysGen) {
        int count = sysGenService.updateSelective(sysGen);
        return count == 1 ? Result.SUCCESS : Result.error(DefaultCodeMsgBundle.UPDATE_ERROR, count);
    }

    @ApiOperation(value = "删除")
    @PreAuthorize("hasAuthority('sys:gen:delete')")
    @PostMapping(value = "/delete")
    public Result delete(@RequestParam Integer id) {
        int count = sysGenService.delete(id);
        return count == 1 ? Result.SUCCESS : Result.error(DefaultCodeMsgBundle.DELETE_ERROR, count);
    }
}
