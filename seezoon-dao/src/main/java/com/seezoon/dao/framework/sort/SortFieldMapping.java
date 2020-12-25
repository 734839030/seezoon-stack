package com.seezoon.dao.framework.sort;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.Assert;

import com.google.common.base.Splitter;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.seezoon.dao.framework.sort.annotation.SortField;

/**
 * @author hdf
 */
public class SortFieldMapping {

    private static final String COLON = ":";

    // <classname,property,dbField>
    private static final Table<String, String, String> SORT_FIELD_MAPPING_CONTAINER = HashBasedTable.create();

    /**
     * 注册排序字段映射
     *
     * @param clazz
     */
    public static void regiest(Class clazz) {
        String className = clazz.getName();
        if (!SORT_FIELD_MAPPING_CONTAINER.containsRow(className)) {
            SortField sortFieldAnnotation = (SortField)clazz.getDeclaredAnnotation(SortField.class);
            if (null != sortFieldAnnotation) {
                String[] value = sortFieldAnnotation.value();
                if (ArrayUtils.isEmpty(value)) {
                    return;
                }
                for (String v : value) {
                    List<String> list = Splitter.on(COLON).trimResults().omitEmptyStrings().splitToList(v);
                    Assert.isTrue(list.size() == 2, "排序字段规则错误xxx:yyyy");
                    SORT_FIELD_MAPPING_CONTAINER.put(className, list.get(0), list.get(1));
                }
            }
        }
    }

    /**
     * 获取DB 排序字段名
     *
     * @param clazz
     * @param fieldName
     * @return
     */
    public static String getDbFieldName(Class clazz, String fieldName) {
        String className = clazz.getName();
        return SORT_FIELD_MAPPING_CONTAINER.get(className, fieldName);
    }
}
