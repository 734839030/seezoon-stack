package com.seezoon.dao.framework.sort.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 排序字段后台别名,放在查询条件类上,主要是防止注入
 *
 * eg. <code>@SortField({"updateTime:t.update_time"})</code>
 *
 * @author hdf
 * @date 2020/6/19 8:11 下午
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SortField {
    String[] value();
}
