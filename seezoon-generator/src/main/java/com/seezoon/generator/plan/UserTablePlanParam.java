package com.seezoon.generator.plan;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.seezoon.generator.constants.TemplateType;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户生成方案参数
 *
 * @author hdf
 */
@Getter
@Setter
public class UserTablePlanParam {

    /**
     * 表名
     */
    @NotEmpty
    @Length(min = 1, max = 50)
    private String tableName;
    /**
     * 菜单名
     */
    @NotEmpty
    @Length(min = 1, max = 50)
    private String menuName;
    /**
     * 模块名
     */
    @NotEmpty
    @Length(min = 1, max = 10)
    private String moduleName;
    /**
     * 功能模块
     */
    @NotEmpty
    @Length(min = 1, max = 20)
    private String functionName;
    /**
     * 生成模板,保留,方便未来生成树结构等 {@link TemplateType}
     */
    @NotEmpty
    private String templateType;
    /**
     * 类名
     */
    @NotEmpty
    @Length(min = 1, max = 50)
    private String className;

    @Valid
    @NotEmpty
    private List<UserColumnPlanParam> userColumnPlanParams;
}
