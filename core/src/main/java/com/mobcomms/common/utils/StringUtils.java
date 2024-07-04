package com.mobcomms.common.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StringUtils {

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static String getDateyyyyMMdd(){
        LocalDate today = LocalDate.now();

        // 날짜 형식을 지정하기
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        // 형식에 맞게 날짜를 문자열로 변환하기
        return today.format(formatter);
    }

}
