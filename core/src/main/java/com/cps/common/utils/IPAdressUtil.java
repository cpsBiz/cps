package com.cps.common.utils;

import jakarta.servlet.http.HttpServletRequest;

public class IPAdressUtil {
	
	// IP 주소 확인
	public static String getIpAdress(HttpServletRequest request) {
		
		String ip = request.getHeader("X-Forwarded-For");
		
		if( ip == null || ip.length() == 0 ) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		
		if( ip == null || ip.length() == 0 ) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		
		if( ip == null || ip.length() == 0 ) {
			ip = request.getRemoteAddr();
		}
		
		return ip;
	}
	
	
}
