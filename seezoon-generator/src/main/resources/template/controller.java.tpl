package com.seezoon.admin.modules.${moduleName}.controller;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.github.pagehelper.PageInfo;
import com.seezoon.admin.modules.${moduleName}.service.${className}Service;
import com.seezoon.dao.modules.${moduleName}.entity.${className};
import com.seezoon.dao.modules.${moduleName}.entity.${className}Condition;
import com.seezoon.framework.api.Result;
import com.seezoon.framework.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

/**
 *${menuName!}
 * @author seezoon-generator ${.now}
 */
@Api(tags = "${menuName!}")
@RestController
@RequestMapping("/${moduleName}")
@RequiredArgsConstructor
public class ${className}Controller extends BaseController {

    private final ${className}Service ${className?uncap_first}Service;

    @ApiOperation(value = "主键查询")
    @PreAuthorize("hasAuthority('${moduleName}:${functionName}:query')")
    @GetMapping("/query/{${pkPlan.javaFieldName}}")
    public Result<${className}> query(@PathVariable ${pkPlan.dataType.javaType()} ${pkPlan.javaFieldName}) {
        ${className} ${className?uncap_first} = ${className?uncap_first}Service.find(${pkPlan.javaFieldName});
        return Result.ok(${className?uncap_first});
    }

    @ApiOperation(value = "分页查询")
    @PreAuthorize("hasAuthority('${moduleName}:${functionName}:query')")
    @PostMapping("/query")
    public Result<PageInfo<${className}>> query(${className}Condition condition) {
        PageInfo<${className}> pageInfo = ${className?uncap_first}Service.find(condition, condition.getPage(), condition.getPageSize());
        return Result.ok(pageInfo);
    }

    @ApiOperation(value = "保存")
    @PreAuthorize("hasAuthority('${moduleName}:${functionName}:save')")
    @PostMapping(value = "/save")
    public Result save(@Valid @RequestBody ${className} ${className?uncap_first}) {
        ${className?uncap_first}Service.save(${className?uncap_first});
        return Result.SUCCESS;
    }

    @ApiOperation(value = "更新")
    @PreAuthorize("hasAuthority('${moduleName}:${functionName}:update')")
    @PostMapping(value = "/update")
    public Result update(@Valid @RequestBody ${className} ${className?uncap_first}) {
        ${className?uncap_first}Service.updateSelective(${className?uncap_first});
        return Result.SUCCESS;
    }

    @ApiOperation(value = "删除")
    @PreAuthorize("hasAuthority('${moduleName}:${functionName}:delete')")
    @PostMapping(value = "/delete")
    public Result delete(@RequestParam ${pkPlan.dataType.javaType()} ${pkPlan.javaFieldName}) {
        ${className?uncap_first}Service.delete(${pkPlan.javaFieldName});
        return Result.SUCCESS;
    }
}
