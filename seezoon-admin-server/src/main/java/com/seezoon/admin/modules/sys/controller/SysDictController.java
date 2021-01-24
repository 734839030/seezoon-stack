package com.seezoon.admin.modules.sys.controller;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.github.pagehelper.PageSerializable;
import com.seezoon.admin.modules.sys.service.SysDictService;
import com.seezoon.dao.modules.sys.entity.SysDict;
import com.seezoon.dao.modules.sys.entity.SysDictCondition;
import com.seezoon.framework.api.DefaultCodeMsgBundle;
import com.seezoon.framework.api.Result;
import com.seezoon.framework.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

/**
 * 字典
 *
 * @author seezoon-generator 2020年12月26日 上午1:50:03
 */
@Api(tags = "字典")
@RestController
@RequestMapping("/sys/dict")
@RequiredArgsConstructor
public class SysDictController extends BaseController {

    private final SysDictService sysDictService;

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
    public Result<PageSerializable<SysDict>> query(@Valid @RequestBody SysDictCondition condition) {
        PageSerializable<SysDict> pageSerializable =
            sysDictService.find(condition, condition.getPage(), condition.getPageSize());
        return Result.ok(pageSerializable);
    }

    @ApiOperation(value = "查询所有分类")
    @GetMapping("/queryTypes")
    public Result<List<String>> queryTypes() {
        return Result.ok(sysDictService.findTypes());
    }

    @ApiOperation(value = "保存")
    @PreAuthorize("hasAuthority('sys:dict:save')")
    @PostMapping(value = "/save")
    public Result save(@Valid @RequestBody SysDict sysDict) {
        int count = sysDictService.save(sysDict);
        return count == 1 ? Result.SUCCESS : Result.error(DefaultCodeMsgBundle.SAVE_ERROR, count);
    }

    @ApiOperation(value = "更新")
    @PreAuthorize("hasAuthority('sys:dict:update')")
    @PostMapping(value = "/update")
    public Result update(@Valid @RequestBody SysDict sysDict) {
        int count = sysDictService.updateSelective(sysDict);
        return count == 1 ? Result.SUCCESS : Result.error(DefaultCodeMsgBundle.UPDATE_ERROR, count);
    }

    @ApiOperation(value = "删除")
    @PreAuthorize("hasAuthority('sys:dict:delete')")
    @PostMapping(value = "/delete")
    public Result delete(@RequestParam Integer id) {
        int count = sysDictService.delete(id);
        return count == 1 ? Result.SUCCESS : Result.error(DefaultCodeMsgBundle.DELETE_ERROR, count);
    }

    @ApiOperation(value = "检查是否重复")
    @PreAuthorize("hasAuthority('sys:dict:query')")
    @PostMapping(value = "/checkTypeAndCode")
    public Result<Boolean> checkTypeAndCode(@RequestParam(required = false) Integer id,
        @NotBlank @RequestParam String type, @NotBlank @RequestParam String code) {
        SysDict sysDict = this.sysDictService.findByTypeAndCode(type, code);
        return Result.ok(null == sysDict || Objects.equals(sysDict.getId(), id));
    }
}
