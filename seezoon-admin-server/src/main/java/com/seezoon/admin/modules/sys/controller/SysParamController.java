package com.seezoon.admin.modules.sys.controller;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.github.pagehelper.PageInfo;
import com.seezoon.admin.modules.sys.service.SysParamService;
import com.seezoon.dao.modules.sys.entity.SysParam;
import com.seezoon.dao.modules.sys.entity.SysParamCondition;
import com.seezoon.framework.api.Result;
import com.seezoon.framework.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

/**
 * @author hdf
 */
@Api(tags = "系统参数")
@RestController
@RequestMapping("/sys")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysParamController extends BaseController {

    private final SysParamService sysParamService;

    @ApiOperation(value = "主键查询")
    @GetMapping("/queryById")
    public Result<SysParam> queryById(@NotNull @ApiParam(value = "主键", required = true) Integer id) {
        SysParam sysParam = sysParamService.findById(id);
        return Result.ok(sysParam);
    }

    @ApiOperation(value = "分页查询")
    @PostMapping("/queryByPage")
    public Result<PageInfo<SysParam>> queryByPage(SysParamCondition condition) {
        PageInfo<SysParam> pageInfo =
            sysParamService.findByPage(condition, condition.getPage(), condition.getPageSize());
        return Result.ok(pageInfo);
    }

    @ApiOperation(value = "保存")
    @PostMapping(value = "/save")
    public Result save(@Validated @RequestBody SysParam sysParam) {
        sysParamService.save(sysParam);
        return Result.SUCCESS;
    }

    @ApiOperation(value = "更新")
    @PostMapping(value = "/update")
    public Result update(@Validated @RequestBody SysParam sysParam) {
        sysParamService.updateSelective(sysParam);
        return Result.SUCCESS;
    }

    @ApiOperation(value = "删除")
    @PostMapping(value = "/delete")
    public Result delete(@NotNull @ApiParam(value = "主键", required = true) Integer id) {
        sysParamService.delete(id);
        return Result.SUCCESS;
    }
}
