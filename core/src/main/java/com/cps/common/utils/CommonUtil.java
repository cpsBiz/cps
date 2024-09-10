package com.cps.common.utils;

import java.lang.reflect.Field;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.HashMap;

public class CommonUtil {

    public static String objectToQueryString(Object obj) throws IllegalAccessException, UnsupportedEncodingException{
        Map<String, String> params = new HashMap<>();

        // 객체의 모든 필드에 대해 반복
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true); // private 필드에 접근 가능하도록 설정

            // 필드의 이름과 값을 추출하여 Map에 추가
            Object value = field.get(obj);
            if (value != null) {
                params.put(field.getName(), value.toString());
            }
        }

        // Map을 Query String으로 변환
        StringBuilder queryString = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (queryString.length() > 0) {
                queryString.append("&");
            }
            queryString.append(entry.getKey());
            queryString.append("=");
            queryString.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return queryString.toString();
    }

}
