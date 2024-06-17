package com.mobcomms.common.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AES256Utils {

    private static final String AES_ALGORITHM = "AES";
    private static final String AES_CIPHER_MODE = "AES/ECB/PKCS5Padding"; // ECB 모드 사용 (보안적으로는 CBC 모드 권장)

    private SecretKey secretKey;

    /**
     * AES256Util 생성자. 랜덤한 키를 생성합니다.
     */
    public AES256Utils() {
        try {
            // AES 키 생성
            KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_ALGORITHM);
            keyGenerator.init(256); // 256비트 키 생성
            this.secretKey = keyGenerator.generateKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 주어진 키로 AES256Util 생성자. 주어진 바이트 배열을 기반으로 키 생성합니다.
     *
     * @param keyBytes 키를 바이트 배열로 표현한 값
     */
    public AES256Utils(byte[] keyBytes) {
        this.secretKey = new SecretKeySpec(keyBytes, AES_ALGORITHM);
    }

    /**
     * 주어진 평문 문자열을 AES256로 암호화하여 Base64로 인코딩된 문자열로 반환합니다.
     *
     * @param plainText 평문 문자열
     * @return 암호화된 문자열 (Base64 인코딩)
     */
    public String encrypt(String plainText) {
        try {
            Cipher cipher = Cipher.getInstance(AES_CIPHER_MODE);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 주어진 Base64로 인코딩된 암호문을 AES256로 복호화하여 평문 문자열을 반환합니다.
     *
     * @param encryptedText Base64로 인코딩된 암호문
     * @return 복호화된 평문 문자열
     */
    public String decrypt(String encryptedText) {
        try {
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
            Cipher cipher = Cipher.getInstance(AES_CIPHER_MODE);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * AES256Util의 현재 키를 Base64로 인코딩하여 문자열로 반환합니다.
     *
     * @return 현재 키를 Base64로 인코딩한 문자열
     */
    public String getKeyAsBase64() {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    /**
     * 주어진 Base64로 인코딩된 AES 키 문자열을 사용하여 AES256Util 객체를 생성합니다.
     *
     * @param base64EncodedKey Base64로 인코딩된 AES 키 문자열
     * @return AES256Util 객체
     */
    public static AES256Utils fromBase64EncodedKey(String base64EncodedKey) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(base64EncodedKey);
            return new AES256Utils(keyBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}