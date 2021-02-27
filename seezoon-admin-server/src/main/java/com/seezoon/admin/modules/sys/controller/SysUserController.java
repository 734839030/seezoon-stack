package com.seezoon.admin.modules.sys.controller;

import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.github.pagehelper.PageSerializable;
import com.seezoon.admin.framework.file.FileService;
import com.seezoon.admin.modules.sys.security.AdminPasswordEncoder;
import com.seezoon.admin.modules.sys.service.SysUserService;
import com.seezoon.dao.modules.sys.entity.SysUser;
import com.seezoon.dao.modules.sys.entity.SysUserCondition;
import com.seezoon.framework.api.DefaultCodeMsgBundle;
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
    private final FileService fileService;

    @ApiOperation(value = "主键查询")
    @PreAuthorize("hasAuthority('sys:user:query')")
    @GetMapping("/query/{id}")
    public Result<SysUser> query(@PathVariable Integer id) {
        SysUser sysUser = sysUserService.find(id);
        sysUser.setPhotoUrl(fileService.getUrl(sysUser.getPhoto()));
        return Result.ok(sysUser);
    }

    @ApiOperation(value = "分页查询")
    @PreAuthorize("hasAuthority('sys:user:query')")
    @PostMapping("/query")
    public Result<PageSerializable<SysUser>> query(@Valid @RequestBody SysUserCondition condition) {
        PageSerializable<SysUser> pageSerializable =
            sysUserService.find(condition, condition.getPage(), condition.getPageSize());
        pageSerializable.getList().forEach(sysUser -> {
            sysUser.setPhotoUrl(fileService.getUrl(sysUser.getPhoto()));
        });
        return Result.ok(pageSerializable);
    }

    @ApiOperation(value = "保存")
    @PreAuthorize("hasAuthority('sys:user:save')")
    @PostMapping(value = "/save")
    public Result save(@Valid @RequestBody SysUser sysUser) {
        sysUser.setPassword(AdminPasswordEncoder.encode(sysUser.getPassword()));
        int count = sysUserService.save(sysUser);
        return count == 1 ? Result.SUCCESS : Result.error(DefaultCodeMsgBundle.SAVE_ERROR, count);
    }

    @ApiOperation(value = "更新")
    @PreAuthorize("hasAuthority('sys:user:update')")
    @PostMapping(value = "/update")
    public Result update(@Valid @RequestBody SysUser sysUser) {
        sysUser.setPassword(AdminPasswordEncoder.encode(sysUser.getPassword()));
        int count = sysUserService.updateSelective(sysUser);
        return count == 1 ? Result.SUCCESS : Result.error(DefaultCodeMsgBundle.UPDATE_ERROR, count);
    }

    @ApiOperation(value = "删除")
    @PreAuthorize("hasAuthority('sys:user:delete')")
    @PostMapping(value = "/delete")
    public Result delete(@RequestParam Integer id) {
        int count = sysUserService.delete(id);
        return count == 1 ? Result.SUCCESS : Result.error(DefaultCodeMsgBundle.DELETE_ERROR, count);
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
