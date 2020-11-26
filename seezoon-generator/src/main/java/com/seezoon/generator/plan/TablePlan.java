package com.seezoon.generator.plan;

import java.util.List;

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
    private String tableName;
    /**
     * 主键类型
     */
    private PkPlan pkPlan;
    /**
     * 菜单名，默认为DB表的备注
     */
    private String menuName;

    /**
     * 模块名，默认为表的前缀
     */
    private String moduleName;
    /**
     * 功能模块，默认为去掉前缀
     */
    private String functionName;
    /**
     * 生成模板,保留,方便未来生成树结构等
     */
    private TemplateType templateType;

    /**
     * 类名，默认表名转大驼峰
     */
    private String className;

    /**
     * 字段方案
     */
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
