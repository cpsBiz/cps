package com.mobcomms.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class MobDateUtils {
    public static String toStrDateTime(LocalDateTime localDateTime) {
        return  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(localDateTime);
    }

    public static LocalDateTime toLocateDateTimeFromYyyymmdd(String yyyymmdd) {
        return LocalDate.parse(yyyymmdd, DateTimeFormatter.ofPattern("yyyyMMdd")).atStartOfDay();
    }

    public static long calculateDateDifference(int date1, int date2) {
        // Convert int date to LocalDate
        LocalDate localDate1 = convertToLocalDate(date1);
        LocalDate localDate2 = convertToLocalDate(date2);

        // Calculate the difference in days
        return ChronoUnit.DAYS.between(localDate1, localDate2);
    }

    public static LocalDate convertToLocalDate(int date) {
        // Convert yyyymmdd int to LocalDate
        String dateString = String.valueOf(date);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return LocalDate.parse(dateString, formatter);
    }

}
