package com.hn.common.constant;

import java.util.HashMap;
import java.util.Map;

public class SortConstant {

    public static final Map<String, String> SORT_MAP = new HashMap<>();

    static {
        SORT_MAP.put("sort_down", "DESC");
        SORT_MAP.put("sort_up", "ASC");
    }

    public static String sortFieldValid(String value) {

        if (value == null || value.isEmpty()) {
            return null;
        }

        String[] sorts = value.split("-");
        if (sorts.length != 2) {
            // 不符合传参规则
            return null;
        }

        if (SORT_MAP.containsKey(sorts[1])) {
            return sorts[0] + "-" + SORT_MAP.get(sorts[1]);
        }

        return null;
    }

}
