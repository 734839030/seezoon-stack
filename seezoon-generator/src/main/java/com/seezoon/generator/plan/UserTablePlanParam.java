package com.seezoon.generator.plan;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import com.seezoon.generator.constants.TemplateType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 用户生成方案参数
 *
 * @author hdf
 */
@Getter
@Setter
@NoArgsConstructor
public class UserTablePlanParam {

    /**
     * 表名
     */
    @NotBlank
    @Size(max = 50)
    private String tableName;
    /**
     * 菜单名
     */
    @NotBlank
    @Size(max = 50)
    private String menuName;
    /**
     * 模块名
     */
    @NotBlank
    @Size(max = 10)
    private String moduleName;
    /**
     * 功能模块
     */
    @NotBlank
    @Size(max = 20)
    private String functionName;
    /**
     * 生成模板,保留,方便未来生成树结构等 {@link TemplateType}
     */
    @NotNull
    private Integer templateType;
    /**
     * 类名
     */
    @NotBlank
    @Length(max = 50)
    private String className;

    @Valid
    @NotEmpty
    private List<UserColumnPlanParam> columnPlans;

}
