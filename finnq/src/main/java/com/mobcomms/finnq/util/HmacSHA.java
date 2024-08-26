package com.mobcomms.finnq.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

@Slf4j
@Service
@RequiredArgsConstructor
public class HmacSHA {
	@Value("${finnqDomain.hmacKey}")
	private String hmacKeyTemp;
	private static String hmacKey;

	@PostConstruct
	public void init() {
		hmacKey = hmacKeyTemp;
	}

	public static String hmacKey(String trsnKey, String alinCd, String userId, String amt) throws Exception {
		String msg = trsnKey.concat(":").concat(alinCd).concat(":").concat(userId).concat(":").concat(amt);
		String hmac = hashDataToHex(hmacKey, msg,"HmacSHA256");
		return  hmac;
	}

	public static String hashDataToHex(String salt, String msg, String ALGORITHM) throws Exception {
		Mac mac = Mac.getInstance(ALGORITHM);
		mac.init(new SecretKeySpec(salt.getBytes("UTF-8"), ALGORITHM));
		return byteArrayToHex(mac.doFinal(msg.getBytes()));
	}

	public static String byteArrayToHex(byte[] a)
	{
		StringBuilder sb = new StringBuilder(a.length *2);
		for(byte b: a)
			sb.append(String.format("%02x", b));
		return sb.toString();
	}
}
