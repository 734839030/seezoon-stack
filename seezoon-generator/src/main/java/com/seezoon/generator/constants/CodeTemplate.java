package com.seezoon.generator.constants;

/**
 * @author hdf
 */
public enum CodeTemplate {

    MAPPER("mapper.xml.ftl", "mappings/%s", "%sMapper.xml"),

    ENTITY("entity.java.ftl", "com/seezoon/dao/modules/%s/entity", "%s.java"),

    DAO("dao.java.ftl", "com/seezoon/dao/modules/%s", "%sDao.java"),

    SERVICE("service.java.ftl", "com/seezoon/admin/modules/%s/service", "%sService.java"),

    CONTROLLER("controller.java.ftl", "com/seezoon/admin/modules/%s/controller", "%sController.java"),

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
