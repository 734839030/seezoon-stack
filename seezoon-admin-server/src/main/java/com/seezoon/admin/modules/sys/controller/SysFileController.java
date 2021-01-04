package com.seezoon.admin.modules.sys.controller;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.github.pagehelper.PageSerializable;
import com.seezoon.admin.modules.sys.service.SysFileService;
import com.seezoon.dao.modules.sys.entity.SysFile;
import com.seezoon.dao.modules.sys.entity.SysFileCondition;
import com.seezoon.framework.api.Result;
import com.seezoon.framework.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

/**
 * 文件
 *
 * @author seezoon-generator 2021年1月2日 上午1:04:41
 */
@Api(tags = "文件")
@RestController
@RequestMapping("/sys/file")
@RequiredArgsConstructor
public class SysFileController extends BaseController {

    private final SysFileService sysFileService;

    @ApiOperation(value = "主键查询")
    @PreAuthorize("hasAuthority('sys:file:query')")
    @GetMapping("/query/{id}")
    public Result<SysFile> query(@PathVariable Integer id) {
        SysFile sysFile = sysFileService.find(id);
        return Result.ok(sysFile);
    }

    @ApiOperation(value = "分页查询")
    @PreAuthorize("hasAuthority('sys:file:query')")
    @PostMapping("/query")
    public Result<PageSerializable<SysFile>> query(@Valid @RequestBody SysFileCondition condition) {
        PageSerializable<SysFile> pageSerializable =
            sysFileService.find(condition, condition.getPage(), condition.getPageSize());
        return Result.ok(pageSerializable);
    }

    @ApiOperation(value = "保存")
    @PreAuthorize("hasAuthority('sys:file:save')")
    @PostMapping(value = "/save")
    public Result save(@Valid @RequestBody SysFile sysFile) {
        sysFileService.save(sysFile);
        return Result.SUCCESS;
    }

    @ApiOperation(value = "更新")
    @PreAuthorize("hasAuthority('sys:file:update')")
    @PostMapping(value = "/update")
    public Result update(@Valid @RequestBody SysFile sysFile) {
        sysFileService.updateSelective(sysFile);
        return Result.SUCCESS;
    }

    @ApiOperation(value = "删除")
    @PreAuthorize("hasAuthority('sys:file:delete')")
    @PostMapping(value = "/delete")
    public Result delete(@RequestParam Integer id) {
        sysFileService.delete(id);
        return Result.SUCCESS;
    }
}
