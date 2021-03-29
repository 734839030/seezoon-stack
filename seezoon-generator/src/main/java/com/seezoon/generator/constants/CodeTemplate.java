package com.seezoon.generator.constants;

/**
 * 生成模板
 *
 * @author hdf
 */
public enum CodeTemplate {

    MAPPER("mapper.xml.tpl", "mappings/${moduleName}", "${className}Mapper.xml"),

    ENTITY("entity.java.tpl", "com/seezoon/dao/modules/${moduleName}/entity", "${className}.java"),

    CONDITION("condition.java.tpl", "com/seezoon/dao/modules/${moduleName}/entity", "${className}Condition.java"),

    DAO("dao.java.tpl", "com/seezoon/dao/modules/${moduleName}", "${className}Dao.java"),

    MENU("menu.sql.tpl", "", "sys_menu_for_${tableName}.sql"),

    SERVICE("service.java.tpl", "com/seezoon/admin/modules/${moduleName}/service", "${className}Service.java"),

    CONTROLLER("controller.java.tpl", "com/seezoon/admin/modules/${moduleName}/controller",
        "${className}Controller.java"),

    ;

    private String tplName;
    private String path;
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
