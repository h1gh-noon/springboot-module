package com.store.jdstore.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

    public static String getTimestampStr() {
        return getTimestampStr(LocalDateTime.now());
    }

    public static String getTimestampStr(LocalDateTime date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return date.format(dateTimeFormatter);
    }

}
