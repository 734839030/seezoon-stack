package com.seezoon.admin.modules.sys.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.seezoon.admin.modules.sys.service.SysMenuService;
import com.seezoon.dao.framework.dto.Tree;
import com.seezoon.dao.modules.sys.entity.SysMenu;
import com.seezoon.framework.api.DefaultCodeMsgBundle;
import com.seezoon.framework.api.Result;
import com.seezoon.framework.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

/**
 * 菜单管理
 *
 * @author seezoon-generator 2021年1月31日 上午12:21:33
 */
@Api(tags = "菜单管理")
@RestController
@RequestMapping("/sys/menu")
@RequiredArgsConstructor
public class SysMenuController extends BaseController {

    private final SysMenuService sysMenuService;

    @ApiOperation(value = "主键查询")
    @PreAuthorize("hasAuthority('sys:menu:query')")
    @GetMapping("/query/{id}")
    public Result<SysMenu> query(@PathVariable Integer id) {
        SysMenu sysMenu = sysMenuService.find(id);
        return Result.ok(sysMenu);
    }

    @ApiOperation(value = "查询树")
    @PreAuthorize("hasAuthority('sys:menu:query')")
    @PostMapping("/query")
    public Result<List<SysMenu>> query() {
        return Result.ok(sysMenuService.findTreeTable());
    }

    @ApiOperation(value = "保存")
    @PreAuthorize("hasAuthority('sys:menu:save')")
    @PostMapping(value = "/save")
    public Result save(@Valid @RequestBody SysMenu sysMenu) {
        int count = sysMenuService.save(sysMenu);
        return count == 1 ? Result.SUCCESS : Result.error(DefaultCodeMsgBundle.SAVE_ERROR, count);
    }

    @ApiOperation(value = "更新")
    @PreAuthorize("hasAuthority('sys:menu:update')")
    @PostMapping(value = "/update")
    public Result update(@Valid @RequestBody SysMenu sysMenu) {
        int count = sysMenuService.updateSelective(sysMenu);
        return count == 1 ? Result.SUCCESS : Result.error(DefaultCodeMsgBundle.UPDATE_ERROR, count);
    }

    @ApiOperation(value = "删除")
    @PreAuthorize("hasAuthority('sys:menu:delete')")
    @PostMapping(value = "/delete")
    public Result delete(@RequestParam Integer id) {
        int count = sysMenuService.delete(id);
        return count >= 1 ? Result.SUCCESS : Result.error(DefaultCodeMsgBundle.DELETE_ERROR, count);
    }

    @ApiOperation(value = "按层级查询")
    @PostMapping(value = "/tree")
    public Result<List<Tree>> tree(@RequestParam Integer parentId,
        @RequestParam(required = false) boolean includeChild) {
        return Result.ok(this.sysMenuService.findTree(parentId, includeChild));
    }

}
