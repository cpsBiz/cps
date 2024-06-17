package com.mobcomms.common.utils;

import java.util.Base64;

public class BASE64Utils {
    /**
     * 주어진 바이트 배열을 Base64 문자열로 인코딩합니다.
     *
     * @param bytes 인코딩할 바이트 배열
     * @return Base64로 인코딩된 문자열
     */
    public static String encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * 주어진 문자열을 Base64로 인코딩합니다.
     *
     * @param text 인코딩할 문자열
     * @return Base64로 인코딩된 문자열
     */
    public static String encode(String text) {
        return encode(text.getBytes());
    }

    /**
     * 주어진 Base64로 인코딩된 문자열을 디코딩하여 바이트 배열로 반환합니다.
     *
     * @param base64Encoded Base64로 인코딩된 문자열
     * @return 디코딩된 바이트 배열
     */
    public static byte[] decode(String base64Encoded) {
        return Base64.getDecoder().decode(base64Encoded);
    }

    /**
     * 주어진 Base64로 인코딩된 문자열을 디코딩하여 문자열로 반환합니다.
     *
     * @param base64Encoded Base64로 인코딩된 문자열
     * @return 디코딩된 문자열
     */
    public static String decodeToString(String base64Encoded) {
        byte[] decodedBytes = decode(base64Encoded);
        return new String(decodedBytes);
    }

    /**
     * 주어진 Base64로 인코딩된 바이트 배열을 디코딩하여 문자열로 반환합니다.
     *
     * @param base64EncodedBytes Base64로 인코딩된 바이트 배열
     * @return 디코딩된 문자열
     */
    public static String decodeToString(byte[] base64EncodedBytes) {
        byte[] decodedBytes = decode(new String(base64EncodedBytes));
        return new String(decodedBytes);
    }
}
