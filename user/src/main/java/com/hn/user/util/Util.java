package com.hn.user.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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

    /**
     * 获取随机字符串 当作token
     *
     * @return String
     */
    public static String getRandomToken() {
        return getRandomStr(64);
    }

    /**
     * 获取随机字符串
     *
     * @return String
     */
    public static String getRandomStr(int length) {

        Random rand = new Random();
        char[] rs = new char[length];
        for (int i = 0; i < length; i++) {
            int t = rand.nextInt(3);
            if (t == 0) {
                // 大写字母
                rs[i] = (char) (rand.nextInt(26) + 65);
            } else if (t == 1) {
                // 小写字母
                rs[i] = (char) (rand.nextInt(26) + 97);
            } else {
                // 数字
                rs[i] = (char) (rand.nextInt(10) + 48);
            }
        }
        return new String(rs);
    }

}
