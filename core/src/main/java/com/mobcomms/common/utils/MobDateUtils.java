package com.mobcomms.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MobDateUtils {
    public static String toStrDateTime(LocalDateTime localDateTime) {
        return  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(localDateTime);
    }

    public static LocalDateTime toLocateDateTimeFromYyyymmdd(String yyyymmdd) {
        return LocalDate.parse(yyyymmdd, DateTimeFormatter.ofPattern("yyyyMMdd")).atStartOfDay();
    }
}
