package com.seezoon.generator.constants;

/**
 * 生成模板
 *
 * @author hdf
 */
public enum CodeTemplate {

    // MAPPER("mapper.xml.tpl", "mappings/%s", "%sMapper.xml"),

    ENTITY("entity.java.tpl", "com/seezoon/dao/modules/%s/entity", "%s.java"),

    CONDITION("condition.java.tpl", "com/seezoon/dao/modules/%s/entity", "%s.java"),

    DAO("dao.java.tpl", "com/seezoon/dao/modules/%s", "%sDao.java"),

    SERVICE("service.java.tpl", "com/seezoon/admin/modules/%s/service", "%sService.java"),

    // CONTROLLER("controller.java.tpl", "com/seezoon/admin/modules/%s/controller", "%sController.java"),

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
