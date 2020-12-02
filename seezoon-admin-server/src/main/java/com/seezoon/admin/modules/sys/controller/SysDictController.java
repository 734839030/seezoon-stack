package com.seezoon.admin.modules.sys.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.github.pagehelper.PageInfo;
import com.seezoon.admin.modules.sys.service.SysDictService;
import com.seezoon.dao.modules.sys.entity.SysDict;
import com.seezoon.dao.modules.sys.entity.SysDictCondition;
import com.seezoon.framework.api.Result;
import com.seezoon.framework.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

/**
 * 字典
 *
 * @author seezoon-generator 2020年12月2日 下午11:35:26
 */
@Api(tags = "数据字典")
@RestController
@RequestMapping("/sys/dict")
@RequiredArgsConstructor
public class SysDictController extends BaseController {

    private final SysDictService sysDictService;

    @ApiOperation(value = "根据类型查询")
    @GetMapping("/query/type/{type}")
    public Result<List<SysDict>> query(@PathVariable String type) {
        return Result.ok(sysDictService.find(type));
    }

    @ApiOperation(value = "主键查询")
    @PreAuthorize("hasAuthority('sys:dict:query')")
    @GetMapping("/query/{id}")
    public Result<SysDict> query(@PathVariable Integer id) {
        SysDict sysDict = sysDictService.find(id);
        return Result.ok(sysDict);
    }

    @ApiOperation(value = "分页查询")
    @PreAuthorize("hasAuthority('sys:dict:query')")
    @PostMapping("/query")
    public Result<PageInfo<SysDict>> query(SysDictCondition condition) {
        PageInfo<SysDict> pageInfo = sysDictService.find(condition, condition.getPage(), condition.getPageSize());
        return Result.ok(pageInfo);
    }

    @ApiOperation(value = "保存")
    @PreAuthorize("hasAuthority('sys:dict:save')")
    @PostMapping(value = "/save")
    public Result save(@Valid @RequestBody SysDict sysDict) {
        sysDictService.save(sysDict);
        return Result.SUCCESS;
    }

    @ApiOperation(value = "更新")
    @PreAuthorize("hasAuthority('sys:dict:update')")
    @PostMapping(value = "/update")
    public Result update(@Valid @RequestBody SysDict sysDict) {
        sysDictService.updateSelective(sysDict);
        return Result.SUCCESS;
    }

    @ApiOperation(value = "删除")
    @PreAuthorize("hasAuthority('sys:dict:delete')")
    @PostMapping(value = "/delete")
    public Result delete(@RequestParam Integer id) {
        sysDictService.delete(id);
        return Result.SUCCESS;
    }
}
