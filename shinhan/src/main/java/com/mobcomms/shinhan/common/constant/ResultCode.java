package com.mobcomms.shinhan.common.constant;

public class ResultCode {
	
	/* ---------------------------------------------------------------------- Result Code ---------------------------------------------------------------------- */
	public static final int RESULT_CODE_0 				= 0;
	
	/* Result Code :: 공통 */
	public static final int RESULT_CODE_1001 			= 1001;														// NullPointerException
	public static final int RESULT_CODE_1002 			= 1002;														// SQLException
	public static final int RESULT_CODE_1003 			= 1003;														// ArrayIndexOutOfBoundsException
	public static final int RESULT_CODE_1004 			= 1004;														// MultipartException, FileUploadException
	public static final int RESULT_CODE_1005 			= 1005;														// IOException
	public static final int RESULT_CODE_1006 			= 1006;														// ParseException
	public static final int RESULT_CODE_1007 			= 1007;														// MappingException
	public static final int RESULT_CODE_1008 			= 1008;														// HttpRequestMethodNotSupportedException, HttpMessageNotReadableException
	public static final int RESULT_CODE_1009 			= 1009;														// IllegalAccessException
	
	/* Result Code :: 공통(Custom) */
	public static final int RESULT_CODE_1101 			= 1101;														// JwtTokenException
	public static final int RESULT_CODE_1102 			= 1102;														// NotDataException
	public static final int RESULT_CODE_1103 			= 1103;														// BadDataException
	
	/* Result Code :: 회원 관련 */
	public static final int RESULT_CODE_2001 			= 2001;
	public static final int RESULT_CODE_2002 			= 2002;
	public static final int RESULT_CODE_2011 			= 2011;
	public static final int RESULT_CODE_2012 			= 2012;
	public static final int RESULT_CODE_2013 			= 2013;
	public static final int RESULT_CODE_2021 			= 2021;
	public static final int RESULT_CODE_2022 			= 2022;
	public static final int RESULT_CODE_2023 			= 2023;
	public static final int RESULT_CODE_2024 			= 2024;
	public static final int RESULT_CODE_2031 			= 2031;
	
	/* Result Code :: 포인트 관련 */
	public static final int RESULT_CODE_3001 			= 3001;
	public static final int RESULT_CODE_3002 			= 3002;
	public static final int RESULT_CODE_3003 			= 3003;
	public static final int RESULT_CODE_3004 			= 3004;
	public static final int RESULT_CODE_3005 			= 3005;
	
	public static final int RESULT_CODE_9999 			= 9999;
	
	
	
	/* ---------------------------------------------------------------------- Result Message ---------------------------------------------------------------------- */
	public static final String RESULT_MSG_0 			= "성공";
	
	/* Result Message :: 공통 */
	public static final String RESULT_MSG_1001			= "NULL 포인트 오류";										// NullPointerException
	public static final String RESULT_MSG_1002			= "데이터베이스 오류";										// SQLException
	public static final String RESULT_MSG_1003 			= "배열 인덱스 오류";										// ArrayIndexOutOfBoundsException
	public static final String RESULT_MSG_1004			= "파일 업로드 오류";										// MultipartException, FileUploadException
	public static final String RESULT_MSG_1005			= "IO 오류";												// IOException
	public static final String RESULT_MSG_1006			= "파싱 오류";												// ParseException
	public static final String RESULT_MSG_1007			= "매핑 오류";												// MappingException
	public static final String RESULT_MSG_1008			= "Method Type 오류";										// HttpRequestMethodNotSupportedException, HttpMessageNotReadableException
	public static final String RESULT_MSG_1009			= "접근권한 오류";											// IllegalAccessException
	
	/* Result Message :: 공통(Custom) */
	public static final String RESULT_MSG_1101			= "%s 토큰 입니다.";										// JwtTokenException
	public static final String RESULT_MSG_1102			= "파라미터 %s 값이 없습니다.";								// NotDataException
	public static final String RESULT_MSG_1103			= "파라미터 %s 값이 지정된 형식에 맞지 않습니다.";			// BadDataException
	
	/* Result Message :: 회원 관련 */
	public static final String RESULT_MSG_2001 			= "회원 정보가 없습니다.";
	public static final String RESULT_MSG_2002 			= "탈퇴한 회원 입니다.";
	
	/* Result Message :: 포인트 관련 */
	public static final String RESULT_MSG_3001 			= "포인트 적립이 실패하였습니다.";
	public static final String RESULT_MSG_3002 			= "아직 적립 가능한 시간이 지나지 않았습니다.";	// "아직 적립 가능한 시간이 지나지 않았습니다.";
	public static final String RESULT_MSG_3003 			= "요청하신 포인트가 적립 가능한 최대 충전 개수를 넘었습니다.";	// "요청하신 포인트가 적립 가능한 최대 포인트를 넘었습니다.";
	public static final String RESULT_MSG_3004 			= "요청하신 포인트가 오늘 남은 적립 가능한 포인트를 넘었습니다.";	// "요청하신 포인트가 오늘 남은 적립 가능한 포인트를 넘었습니다.";
	public static final String RESULT_MSG_3005 			= "일일 적립 가능한 최대 충전 개수를 초과했습니다.";	// "일일 적립 가능한 최대 포인트를 초과했습니다.";
	public static final String RESULT_MSG_9999			= "에러코드 미정의";
	
	
	public static int getCode(Object code) {
		int result;
		try {
			result = ResultCode.class.getField("RESULT_CODE_"+code).getInt(0);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			result = RESULT_CODE_9999;
		}
		
		return result;
	}
	
	public static String getMsg(Object code) {
		String result;
		try {
			result = ResultCode.class.getField("RESULT_MSG_"+code).get(0).toString();
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			result = RESULT_MSG_9999;
		}
		
		return result;
	}
	
	
	
}
