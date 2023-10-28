package com.user.userserver.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Util {

    // Object 转 Map<String, Object>
    public static Map<String, Object> getObjectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<String, Object>();
        if (obj == null) {
            return map;
        }
        Class<?> cla = obj.getClass();
        Field[] fields = cla.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String keyName = field.getName();
            Object value = field.get(obj);
            if (value != null) {
                map.put(keyName, value);
            }
        }
        return map;
    }

}
