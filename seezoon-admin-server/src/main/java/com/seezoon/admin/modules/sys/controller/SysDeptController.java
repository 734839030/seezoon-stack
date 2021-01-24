package com.seezoon.admin.modules.sys.controller;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.github.pagehelper.PageSerializable;
import com.seezoon.admin.modules.sys.service.SysDeptService;
import com.seezoon.dao.framework.dto.Tree;
import com.seezoon.dao.modules.sys.entity.SysDept;
import com.seezoon.dao.modules.sys.entity.SysDeptCondition;
import com.seezoon.framework.api.DefaultCodeMsgBundle;
import com.seezoon.framework.api.Result;
import com.seezoon.framework.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

/**
 * 组织机构
 *
 * @author seezoon-generator 2021年1月12日 下午10:54:44
 */
@Api(tags = "部门管理")
@RestController
@RequestMapping("/sys/dept")
@RequiredArgsConstructor
public class SysDeptController extends BaseController {

    private final SysDeptService sysDeptService;

    @ApiOperation(value = "主键查询")
    @PreAuthorize("hasAuthority('sys:dept:query')")
    @GetMapping("/query/{id}")
    public Result<SysDept> query(@PathVariable Integer id) {
        SysDept sysDept = sysDeptService.find(id);
        return Result.ok(sysDept);
    }

    @ApiOperation(value = "分页查询")
    @PreAuthorize("hasAuthority('sys:dept:query')")
    @PostMapping("/query")
    public Result<PageSerializable<SysDept>> query(@Valid @RequestBody SysDeptCondition condition) {
        PageSerializable<SysDept> pageSerializable =
            sysDeptService.find(condition, condition.getPage(), condition.getPageSize());
        return Result.ok(pageSerializable);
    }

    @ApiOperation(value = "保存")
    @PreAuthorize("hasAuthority('sys:dept:save')")
    @PostMapping(value = "/save")
    public Result save(@Valid @RequestBody SysDept sysDept) {
        int count = sysDeptService.save(sysDept);
        return count == 1 ? Result.SUCCESS : Result.error(DefaultCodeMsgBundle.SAVE_ERROR, count);
    }

    @ApiOperation(value = "更新")
    @PreAuthorize("hasAuthority('sys:dept:update')")
    @PostMapping(value = "/update")
    public Result update(@Valid @RequestBody SysDept sysDept) {
        int count = sysDeptService.updateSelective(sysDept);
        return count == 1 ? Result.SUCCESS : Result.error(DefaultCodeMsgBundle.UPDATE_ERROR, count);
    }

    @ApiOperation(value = "删除")
    @PreAuthorize("hasAuthority('sys:dept:delete')")
    @PostMapping(value = "/delete")
    public Result delete(@RequestParam Integer id) {
        int count = sysDeptService.delete(id);
        return count == 1 ? Result.SUCCESS : Result.error(DefaultCodeMsgBundle.DELETE_ERROR, count);
    }

    @ApiOperation(value = "检查是否重复")
    @PreAuthorize("hasAuthority('sys:dept:query')")
    @PostMapping(value = "/checkName")
    public Result<Boolean> checkName(@RequestParam(required = false) Integer id, @NotBlank @RequestParam String name,
        @NotNull @RequestParam(defaultValue = "0") Integer parentId) {
        SysDept sysDept = this.sysDeptService.findByNameAndParentId(name, parentId);
        return Result.ok(null == sysDept || Objects.equals(sysDept.getId(), id));
    }

    @ApiOperation(value = "按层级查询部门树")
    @PostMapping(value = "/tree")
    public Result<List<Tree>> tree(@RequestParam Integer parentId,
        @RequestParam(required = false) boolean includeChild) {
        return Result.ok(this.sysDeptService.findTree(parentId, includeChild));
    }
}
