package com.mobcomms.shinhan.common.util;

import com.mobcomms.shinhan.common.constant.Constant;
import com.mobcomms.shinhan.common.constant.ResultCode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 결과 처리 Utility
 * created on : 2024-03-04
 * created by : 서철환, 인라이플 모비컴즈사업부
 * history
 * 。
 * 。
 */
public class ResponseUtils {
	
	
	/* 단일 결과 데이터 */
	public static Map<String, Object> response(Map<String, Object> response, Object dataMap) {
		
		/* result, data Object 선언 */
		Map<String, Object> result = new HashMap<String, Object>();
		
		/* 결과 코드(메시지) */
		result.put(Constant.CODE		,Constant.RESULT_SUCCESS);
		
		/* 결과 데이터 */
		response.put(Constant.RESULT	,result);
		response.put(Constant.DATA		,dataMap);
		
		return response;
	}
	
	/* 다중 결과 데이터 */
	public static Map<String, Object> responses(Map<String, Object> response, String[] dataKey, List<Object> dataList) {
		
		/* result, data Object 선언 */
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		
		/* 결과 코드(메시지) */
		result.put(Constant.CODE		,Constant.RESULT_SUCCESS);
		
		/* 결과 데이터 */
		for( int i=0; i<dataKey.length; i++ ) {
			
			data.put(dataKey[i], dataList.get(i));
		}
		
		response.put(Constant.RESULT	,result);
		response.put(Constant.DATA		,data);
        
        return response;
    }
	
	/* Non 결과 데이터 */
	public static Map<String, Object> response(Map<String, Object> response) {
		
		/* result, data Object 선언 */
		Map<String, Object> result = new HashMap<String, Object>();
		
		/* 결과 코드(메시지) */
		result.put(Constant.CODE		,Constant.RESULT_SUCCESS);
		
		response.put(Constant.RESULT	,result);
		
		return response;
	}
    
    
}