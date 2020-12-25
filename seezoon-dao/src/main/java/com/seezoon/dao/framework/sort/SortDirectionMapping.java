package com.seezoon.dao.framework.sort;

import java.util.HashMap;
import java.util.Map;

/**
 * DB排序映射,适应不同前端框架
 *
 * @author hdf
 */
public class SortDirectionMapping {

    private static final Map<String, String> SORT_DIRECTION_MAPPING = new HashMap<String, String>() {
        {
            put("ascending", "asc");
            put("ascend", "asc");
            put("asc", "asc");
            put("descending", "desc");
            put("descend", "desc");
            put("desc", "desc");
        }
    };

    public static String getMapping(String sortOrder) {
        return SORT_DIRECTION_MAPPING.get(sortOrder);
    }
}
