package com.seezoon.admin.modules.sys.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.github.pagehelper.PageSerializable;
import com.seezoon.admin.modules.sys.service.SysDeptService;
import com.seezoon.dao.framework.dto.Tree;
import com.seezoon.dao.modules.sys.entity.SysDept;
import com.seezoon.dao.modules.sys.entity.SysDeptCondition;
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
        sysDeptService.save(sysDept);
        return Result.SUCCESS;
    }

    @ApiOperation(value = "更新")
    @PreAuthorize("hasAuthority('sys:dept:update')")
    @PostMapping(value = "/update")
    public Result update(@Valid @RequestBody SysDept sysDept) {
        sysDeptService.updateSelective(sysDept);
        return Result.SUCCESS;
    }

    @ApiOperation(value = "删除")
    @PreAuthorize("hasAuthority('sys:dept:delete')")
    @PostMapping(value = "/delete")
    public Result delete(@RequestParam Integer id) {
        sysDeptService.delete(id);
        return Result.SUCCESS;
    }

    @ApiOperation(value = "检查是否重复")
    @PreAuthorize("hasAuthority('sys:dept:query')")
    @PostMapping(value = "/checkName")
    public Result<Boolean> checkName(@RequestParam(required = false) Integer id, @NotBlank @RequestParam String name) {
        SysDept sysDept = this.sysDeptService.findByName(name);
        return Result.ok(null == sysDept || Objects.equals(sysDept.getId(), id));
    }

    @ApiOperation(value = "查询部门树")
    @GetMapping(value = "/tree")
    public Result<List<Tree>> tree(@RequestParam Integer parentId) {
        List<Tree> trees = new ArrayList<>();
        List<SysDept> depts = sysDeptService.findByParentId(parentId);
        depts.forEach((dept) -> {
            trees.add(Tree.builder().key(dept.getId()).title(dept.getName()).selectable(true).build());
        });
        return Result.ok(trees);
    }
}
