package com.seezoon.generator.dto.plan;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import com.seezoon.generator.constants.TemplateType;

import lombok.Getter;
import lombok.Setter;

/**
 * 根据表获得的生成方案
 *
 * @author hdf
 */
@Getter
@Setter
public class TablePlan {

    /**
     * 表名
     */
    @NotEmpty
    @Size(min = 1, max = 32)
    private String tableName;

    /**
     * 菜单名，默认为DB表的备注
     */
    @NotEmpty
    @Length(min = 1, max = 50)
    private String menuName;

    /**
     * 模块名，默认为表的前缀
     */
    @NotEmpty
    @Length(min = 1, max = 10)
    private String moduleName;
    /**
     * 功能模块，默认为去掉前缀
     */
    @NotEmpty
    @Length(min = 1, max = 20)
    private String functionName;
    /**
     * 生成模板,保留,方便未来生成树结构等
     */
    @NotNull
    private TemplateType templateType;

    /**
     * 类名，默认表名转大驼峰
     */
    @NotEmpty
    @Length(min = 1, max = 50)
    private String className;

    /**
     * 字段方案
     */
    @Valid
    @NotEmpty
    private List<ColumnPlan> columnPlans;
    /**
     * 是否引入Date
     */
    private boolean importDate;
    /**
     * 是否引入BigDecimal
     */
    private boolean importBigDecimal;
    /**
     * 是否有大字段
     */
    private boolean hasBlob;
    /**
     * 是否有搜索
     */
    private boolean hasSearch;
    /**
     * 是否富文本
     */
    private boolean hasRichTextWidget;
    /**
     * 日期控件
     */
    private boolean hasDateWidget;
    /**
     * 是否有文件上传
     */
    private boolean hasFileUploadWidget;
    /**
     * 图片上传
     */
    private boolean hasImageUploadWidget;

}
