package com.hn.common.util;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static io.micrometer.common.util.StringUtils.isNotBlank;

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

    public static String getTimestampStr() {
        return getTimestampStr(LocalDateTime.now());
    }

    public static String getTimestampStr(LocalDateTime date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return date.format(dateTimeFormatter);
    }

    public static String getTimeRandom() {
        return System.currentTimeMillis() + ((int) ((Math.random() * 100000) + Math.random() * 10000) + "");
    }


    /**
     * 驼峰转下划线
     */
    public static String camelToUnderline(String param) {
        if (isNotBlank(param)) {
            int len = param.length();
            StringBuilder sb = new StringBuilder(len);
            for (int i = 0; i < len; i++) {
                char c = param.charAt(i);
                if (Character.isUpperCase(c)) {
                    sb.append('_');
                    sb.append(Character.toLowerCase(c));
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        } else {
            return "";
        }
    }

    /**
     * 下划线转驼峰
     */
    public static String underlineToCamel(String param) {
        if (isNotBlank(param)) {
            int len = param.length();
            StringBuilder sb = new StringBuilder(len);
            for (int i = 0; i < len; i++) {
                char c = param.charAt(i);
                if (c == 95) {
                    ++i;
                    if (i < len) {
                        sb.append(Character.toUpperCase(param.charAt(i)));
                    }
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        } else {
            return "";
        }
    }

    /**
     * 下划线转驼峰
     */
    public static <T> Map<String, T> underlineToCamelByMap(Map<String, T> map) {

        Map<String, T> resultMap = new HashMap<>();

        map.keySet().forEach(e -> {
            resultMap.put(underlineToCamel(e), map.get(e));
        });
        return resultMap;
    }

    /**
     * 下划线转驼峰
     */
    public static <T> List<Map<String, T>> underlineToCamelByListMap(List<Map<String, T>> list) {

        List<Map<String, T>> resultList = new ArrayList<>();
        list.forEach(map -> {
            Map<String, T> resultMap = new HashMap<>();
            map.keySet().forEach(e -> {
                resultMap.put(underlineToCamel(e), map.get(e));
            });
            resultList.add(resultMap);
        });
        return resultList;
    }

    public static <K, V> void removeNonValue(Map<K, V> map) {
        map.values().removeIf(e -> {
            if (e == null) {
                return true;
            } else if (e instanceof String) {
                return ((String) e).isEmpty();
            } else if (e instanceof List) {
                return ((List<?>) e).isEmpty();
            } else if (e instanceof Map) {
                return ((Map<?, ?>) e).isEmpty();
            }
            return false;
        });
    }
}
