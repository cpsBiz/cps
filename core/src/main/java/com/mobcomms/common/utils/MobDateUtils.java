package com.mobcomms.common.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MobDateUtils {
    public static String toStrDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return formatter.format(localDateTime);
    }
}
