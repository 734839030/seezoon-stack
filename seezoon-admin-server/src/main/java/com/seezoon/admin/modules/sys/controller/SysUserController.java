package com.seezoon.admin.modules.sys.controller;

import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.github.pagehelper.PageSerializable;
import com.seezoon.admin.modules.sys.service.SysUserService;
import com.seezoon.dao.modules.sys.entity.SysUser;
import com.seezoon.dao.modules.sys.entity.SysUserCondition;
import com.seezoon.framework.api.Result;
import com.seezoon.framework.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

/**
 * 用户信息
 *
 * @author seezoon-generator 2021年1月16日 下午11:55:54
 */
@Api(tags = "用户信息")
@RestController
@RequestMapping("/sys/user")
@RequiredArgsConstructor
public class SysUserController extends BaseController {

    private final SysUserService sysUserService;

    @ApiOperation(value = "主键查询")
    @PreAuthorize("hasAuthority('sys:user:query')")
    @GetMapping("/query/{id}")
    public Result<SysUser> query(@PathVariable Integer id) {
        SysUser sysUser = sysUserService.find(id);
        return Result.ok(sysUser);
    }

    @ApiOperation(value = "分页查询")
    @PreAuthorize("hasAuthority('sys:user:query')")
    @PostMapping("/query")
    public Result<PageSerializable<SysUser>> query(@Valid @RequestBody SysUserCondition condition) {
        PageSerializable<SysUser> pageSerializable =
            sysUserService.find(condition, condition.getPage(), condition.getPageSize());
        return Result.ok(pageSerializable);
    }

    @ApiOperation(value = "保存")
    @PreAuthorize("hasAuthority('sys:user:save')")
    @PostMapping(value = "/save")
    public Result save(@Valid @RequestBody SysUser sysUser) {
        sysUserService.save(sysUser);
        return Result.SUCCESS;
    }

    @ApiOperation(value = "更新")
    @PreAuthorize("hasAuthority('sys:user:update')")
    @PostMapping(value = "/update")
    public Result update(@Valid @RequestBody SysUser sysUser) {
        sysUserService.updateSelective(sysUser);
        return Result.SUCCESS;
    }

    @ApiOperation(value = "删除")
    @PreAuthorize("hasAuthority('sys:user:delete')")
    @PostMapping(value = "/delete")
    public Result delete(@RequestParam Integer id) {
        sysUserService.delete(id);
        return Result.SUCCESS;
    }

    @ApiOperation(value = "检查登录名是否重复")
    @PreAuthorize("hasAuthority('sys:user:query')")
    @PostMapping(value = "/checkUsername")
    public Result<Boolean> checkUsername(@RequestParam(required = false) Integer id,
        @NotBlank @RequestParam String username) {
        SysUser sysUser = this.sysUserService.findByUsername(username);
        return Result.ok(null == sysUser || Objects.equals(sysUser.getId(), id));
    }

    @ApiOperation(value = "检查手机号是否重复")
    @PreAuthorize("hasAuthority('sys:user:query')")
    @PostMapping(value = "/checkMobile")
    public Result<Boolean> checkMobile(@RequestParam(required = false) Integer id,
        @NotBlank @RequestParam String mobile) {
        SysUser sysUser = this.sysUserService.findByMobile(mobile);
        return Result.ok(null == sysUser || Objects.equals(sysUser.getId(), id));
    }
}
