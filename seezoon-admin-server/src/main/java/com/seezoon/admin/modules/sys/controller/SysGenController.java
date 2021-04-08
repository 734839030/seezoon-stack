package com.seezoon.admin.modules.sys.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageSerializable;
import com.seezoon.admin.modules.sys.service.SysGenService;
import com.seezoon.dao.modules.sys.entity.SysGen;
import com.seezoon.dao.modules.sys.entity.SysGenCondition;
import com.seezoon.framework.api.DefaultCodeMsgBundle;
import com.seezoon.framework.api.Result;
import com.seezoon.framework.exception.BusinessException;
import com.seezoon.framework.web.BaseController;
import com.seezoon.generator.plan.TablePlan;
import com.seezoon.generator.plan.UserColumnPlanParam;
import com.seezoon.generator.plan.UserTablePlanParam;
import com.seezoon.generator.service.UserGeneratorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

/**
 * 代码生成
 *
 * @author seezoon-generator 2021年3月29日 下午11:27:05
 */
@Api(tags = "代码生成")
@RestController
@RequestMapping("/sys/gen")
@RequiredArgsConstructor
public class SysGenController extends BaseController {

    private final SysGenService sysGenService;
    private final UserGeneratorService userGeneratorService;

    @ApiOperation(value = "主键查询")
    @PreAuthorize("hasAuthority('sys:gen:query')")
    @GetMapping("/query/{id}")
    public Result<TablePlan> query(@PathVariable Integer id) {
        TablePlan tablePlan = getCustomTablePlan(id);
        return Result.ok(tablePlan);
    }

    @ApiOperation(value = "查询默认生成方案")
    @PreAuthorize("hasAuthority('sys:gen:query')")
    @GetMapping("/query")
    public Result<TablePlan> query(@NotBlank @RequestParam String tableName) {
        TablePlan defaultTablePlan = userGeneratorService.findDefaultTablePlan(tableName);
        return Result.ok(defaultTablePlan);
    }

    @ApiOperation(value = "数据库表")
    @PreAuthorize("hasAuthority('sys:gen:query')")
    @GetMapping("/tables")
    public Result<List<String>> tables() {
        List<String> tables = userGeneratorService.findTables();
        return Result.ok(tables);
    }

    @ApiOperation(value = "分页查询")
    @PreAuthorize("hasAuthority('sys:gen:query')")
    @PostMapping("/query")
    public Result<PageSerializable<SysGen>> query(@Valid @RequestBody SysGenCondition condition) {
        PageSerializable<SysGen> pageSerializable =
            sysGenService.find(condition, condition.getPage(), condition.getPageSize());
        return Result.ok(pageSerializable);
    }

    @ApiOperation(value = "保存")
    @PreAuthorize("hasAuthority('sys:gen:save')")
    @PostMapping(value = "/save")
    public Result save(@Valid @RequestBody UserTablePlanParam userTablePlanParam) {
        int count = sysGenService.save(null, userTablePlanParam);
        return count == 1 ? Result.SUCCESS : Result.error(DefaultCodeMsgBundle.SAVE_ERROR, count);
    }

    @ApiOperation(value = "更新")
    @PreAuthorize("hasAuthority('sys:gen:update')")
    @PostMapping(value = "/update/{id}")
    public Result update(@PathVariable Integer id, @Valid @RequestBody UserTablePlanParam userTablePlanParam) {
        int count = sysGenService.save(id, userTablePlanParam);
        return count == 1 ? Result.SUCCESS : Result.error(DefaultCodeMsgBundle.UPDATE_ERROR, count);
    }

    @ApiOperation(value = "删除")
    @PreAuthorize("hasAuthority('sys:gen:delete')")
    @PostMapping(value = "/delete")
    public Result delete(@RequestParam Integer id) {
        int count = sysGenService.delete(id);
        return count == 1 ? Result.SUCCESS : Result.error(DefaultCodeMsgBundle.DELETE_ERROR, count);
    }

    @ApiOperation(value = "生成")
    @PreAuthorize("hasAuthority('sys:gen:generate')")
    @GetMapping(value = "/generate/{id}")
    public void generate(@PathVariable Integer id, HttpServletResponse response) {
        UserTablePlanParam userTablePlanParam = this.getUserTablePlanParam(id);
        try (ServletOutputStream outputStream = response.getOutputStream();) {
            response.setContentType("application/zip");
            response.setHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode(
                    userTablePlanParam.getTableName() + "_" + userTablePlanParam.getMenuName() + "_" + id + ".zip",
                    StandardCharsets.UTF_8));
            userGeneratorService.generate(userTablePlanParam, outputStream);
        } catch (IOException e) {
            throw new BusinessException("下载文件出错:%s", e.getMessage());
        }

    }

    private TablePlan getCustomTablePlan(Integer id) {
        UserTablePlanParam userTablePlanParam = getUserTablePlanParam(id);
        TablePlan tablePlan = userGeneratorService.customTablePlan(userTablePlanParam);
        return tablePlan;
    }

    private UserTablePlanParam getUserTablePlanParam(Integer id) {
        SysGen sysGen = this.sysGenService.find(id);
        Assert.notNull(sysGen, "sys gen data not exist");
        UserTablePlanParam userTablePlanParam = new UserTablePlanParam();
        userTablePlanParam.setTableName(sysGen.getTableName());
        userTablePlanParam.setMenuName(sysGen.getMenuName());
        userTablePlanParam.setModuleName(sysGen.getModuleName());
        userTablePlanParam.setFunctionName(sysGen.getFunctionName());
        userTablePlanParam.setTemplateType(sysGen.getTemplate());
        userTablePlanParam.setClassName(sysGen.getClassName());
        userTablePlanParam.setColumnPlans(JSON.parseArray(sysGen.getColumns(), UserColumnPlanParam.class));
        return userTablePlanParam;
    }
}
