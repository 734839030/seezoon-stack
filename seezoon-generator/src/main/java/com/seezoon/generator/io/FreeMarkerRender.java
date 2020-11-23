package com.seezoon.generator.io;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreeMarkerRender {

    public static final String DEFAULT_DIRECTORY = "template";
    private static Configuration cfg = buildConfiguration(DEFAULT_DIRECTORY);

    public static Configuration buildConfiguration(String directory) {
        Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        cfg.setClassLoaderForTemplateLoading(FreeMarkerRender.class.getClassLoader(), DEFAULT_DIRECTORY);
        return cfg;
    }

    /**
     * @param name
     *            相对路径下的模板
     * @param data
     *            数据
     * @param out
     *            输出流
     */
    public static void renderTemplate(String name, Object data, Writer out) {
        try {
            Template template = cfg.getTemplate(name);
            template.process(data, out);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取渲染后的文本
     *
     * @param name
     *            相对路径下的模板
     * @param data
     *            数据
     */
    public static String renderTemplate(String name, Object data) {
        String result = null;
        try (StringWriter out = new StringWriter()) {
            Template template = cfg.getTemplate(name);
            template.process(data, out);
            result = out.toString();
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 模板内容
     *
     * @param name
     *            相对路径下的模板
     */
    public static String readTemplate(String name) {
        try {
            Template template = cfg.getTemplate(name);
            return template.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 字符串模板
     *
     * @param templateString
     * @param data
     * @return
     */
    public static String renderStringTemplate(String templateString, Object data) {
        Template template;
        try (StringWriter sw = new StringWriter()) {
            template = new Template(null, new StringReader(templateString), cfg);
            template.process(data, sw);
            return sw.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
