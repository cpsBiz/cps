package com.mobcomms.common.utils;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Validation Utility 모음
 * history
 * 。
 * 。
 */
public class ValidateUtil {
	
	// RequestParam 빈 값 체크 :: NotDataException
	public static boolean validateNotParam(String value) {
		
		if( "".equals(value) || value == null ) {
			return false;
		}
		
		return true;
	}
	
	// RequestBody 빈 값 체크 :: NotDataException
	public static boolean validateNotBody(Map<String, Object> body, String key) {
		
		String value = com.mobcomms.common.utils.TextUtil.nvl(body.get(key));
		
		if( "".equals(value) || value == null ) {
			return false;
		}
		
		return true;
	}
	
	// RequestPart 빈 값 체크 :: NotDataException
	public static boolean validateNotPart(MultipartFile file) {
		
		if( file == null || file.isEmpty() ) {
			return false;
		}
		
		return true;
	}
	
	// RequestParam 값 형식 체크 :: BadDataException
	public static boolean validateBadParam(String value, String[] format) {
		
		if( "".equals(value) || value == null ) {
			return true;
		} else {
			boolean valid = false;
			
			for( String str : format ) {
				if( str.equals(value) ) {
					valid = true;
					break;
				}
			}
			
			return valid;
		}
	}
	
	// RequestBody 값 형식 체크 :: BadDataException
	public static boolean validateBadBody(Map<String, Object> body, String key, String[] format) {
		
		String value = com.mobcomms.common.utils.TextUtil.nvl(body.get(key));
		
		if( "".equals(value) || value == null ) {
			return true;
		} else {
			boolean valid = false;
			
			for( String str : format ) {
				if( str.equals(value) ) {
					valid = true;
					break;
				}
			}
			
			return valid;
		}
	}
	
	
}
