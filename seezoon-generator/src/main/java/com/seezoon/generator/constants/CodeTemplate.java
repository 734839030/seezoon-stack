package com.seezoon.generator.constants;

/**
 * 生成模板
 *
 * @author hdf
 */
public enum CodeTemplate {

    MAPPER("mapper.xml.tpl", "seezoon-dao/src/main/resources/mappings/${moduleName}", "${className}Mapper.xml"),

    ENTITY("entity.java.tpl", "seezoon-dao/src/main/java/com/seezoon/dao/modules/${moduleName}/entity",
        "${className}.java"),

    CONDITION("condition.java.tpl", "seezoon-dao/src/main/java/com/seezoon/dao/modules/${moduleName}/entity",
        "${className}Condition.java"),

    DAO("dao.java.tpl", "seezoon-dao/src/main/java/com/seezoon/dao/modules/${moduleName}", "${className}Dao.java"),

    MENU("menu.sql.tpl", "db", "sys_menu_for_${tableName}.sql"),

    SERVICE("service.java.tpl", "seezoon-admin-server/src/main/java/com/seezoon/admin/modules/${moduleName}/service",
        "${className}Service.java"),

    CONTROLLER("controller.java.tpl",
        "seezoon-admin-server/src/main/java/com/seezoon/admin/modules/${moduleName}/controller",
        "${className}Controller.java"),

    // 前端代码
    VUE_INDEX("vue/index.vue.tpl", "seezoon-admin-web/src/views/${moduleName}/${functionName}", "index.vue"),
    // 分页表格
    VUE_MAIN_TABLE("vue/MainTable.vue.tpl", "seezoon-admin-web/src/views/${moduleName}/${functionName}",
        "MainTable.vue"),
    // 添加与编辑模态窗口
    VUE_DATA_FORM_MODAL("vue/DataFormModal.vue.tpl", "seezoon-admin-web/src/views/${moduleName}/${functionName}",
        "DataFormModal.vue"),
    // 查看
    VUE_DATA_VIEW_MODAL("vue/DataViewModal.vue.tpl", "seezoon-admin-web/src/views/${moduleName}/${functionName}",
        "DataViewModal.vue"),
    // 数据字典
    VUE_DATA_DICT("vue/data.ts.tpl", "seezoon-admin-web/src/views/${moduleName}/${functionName}", "data.ts"),

    ;

    // 模板名，相对路径
    private String tplName;
    // 生成路径
    private String path;
    // 文件名
    private String fileName;

    CodeTemplate(String tplName, String path, String fileName) {
        this.tplName = tplName;
        this.path = path;
        this.fileName = fileName;
    }

    public String tplName() {
        return tplName;
    }

    public String path() {
        return path;
    }

    public String fileName() {
        return fileName;
    }
}
