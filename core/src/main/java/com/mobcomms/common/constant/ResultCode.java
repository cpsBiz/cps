package com.mobcomms.common.constant;

public class ResultCode {

	/* ---------------------------------------------------------------------- Result Code ---------------------------------------------------------------------- */
	public static final String RESULT_CODE_0 				= "0";
	public static final String RESULT_CODE_0000 			= "0000";
	public static final String RESULT_STRING_CODE_0 		= "0";

	/* Result Code :: 공통 */
	public static final String RESULT_CODE_1001 			= "1001";														// NullPointerException
	public static final String RESULT_CODE_1002 			= "1002";														// SQLException
	public static final String RESULT_CODE_1003 			= "1003";														// ArrayIndexOutOfBoundsException
	public static final String RESULT_CODE_1004 			= "1004";														// MultipartException, FileUploadException
	public static final String RESULT_CODE_1005 			= "1005";														// IOException
	public static final String RESULT_CODE_1006 			= "1006";														// ParseException
	public static final String RESULT_CODE_1007 			= "1007";														// MappingException
	public static final String RESULT_CODE_1008 			= "1008";														// HttpRequestMethodNotSupportedException, HttpMessageNotReadableException
	public static final String RESULT_CODE_1009 			= "1009";														// IllegalAccessException

	/* Result Code :: 공통(Custom) */
	public static final String RESULT_CODE_1101 			= "1101";														// JwtTokenException
	public static final String RESULT_CODE_1102 			= "1102";														// NotDataException
	public static final String RESULT_CODE_1103 			= "1103";														// BadDataException

	/* Result Code :: 회원 관련 */
	public static final String RESULT_CODE_2001 			= "2001";
	public static final String RESULT_CODE_2002 			= "2002";
	public static final String RESULT_CODE_2003 			= "2003";
	public static final String RESULT_CODE_2011 			= "2011";
	public static final String RESULT_CODE_2012 			= "2012";
	public static final String RESULT_CODE_2013 			= "2013";
	public static final String RESULT_CODE_2014 			= "2014";
	public static final String RESULT_CODE_2021 			= "2021";
	public static final String RESULT_CODE_2022 			= "2022";
	public static final String RESULT_CODE_2023 			= "2023";
	public static final String RESULT_CODE_2024 			= "2024";
	public static final String RESULT_CODE_2031 			= "2031";
	public static final String RESULT_CODE_2041 			= "2041";
	public static final String RESULT_CODE_2042 			= "2042";
	public static final String RESULT_CODE_2043 			= "2043";
	public static final String RESULT_CODE_2051 			= "2051";
	public static final String RESULT_CODE_2052 			= "2052";
	public static final String RESULT_CODE_2053 			= "2053";

	/* Result Code :: 포인트 관련 */
	public static final String RESULT_CODE_3001 			= "3001";
	public static final String RESULT_CODE_3002 			= "3002";
	public static final String RESULT_CODE_3003 			= "3003";
	public static final String RESULT_CODE_3004 			= "3004";
	public static final String RESULT_CODE_3005 			= "3005";
	public static final String RESULT_CODE_3006 			= "3006";
	public static final String RESULT_CODE_3007 			= "3007";

	/* Result Code :: 오퍼월 관련 */
	public static final String RESULT_CODE_5001 			= "5001";
	public static final String RESULT_CODE_5002 			= "5002";
	public static final String RESULT_CODE_5003 			= "5003";
	public static final String RESULT_CODE_5004 			= "5004";
	public static final String RESULT_CODE_5005 			= "5005";

	public static final String RESULT_CODE_9999 			= "9999";



	/* ---------------------------------------------------------------------- Result Message ---------------------------------------------------------------------- */
	public static final String RESULT_MSG_0 			= "성공";

	/* Result Message :: 공통 */
	public static final String RESULT_MSG_1001			= "NULL 포인트 오류";										// NullPointerException
	public static final String RESULT_MSG_1002			= "데이터베이스 오류";										// SQLException
	public static final String RESULT_MSG_1003 			= "배열 인덱스 오류";										// ArrayIndexOutOfBoundsException
	public static final String RESULT_MSG_1004			= "파일 업로드 오류";										// MultipartException, FileUploadException
	public static final String RESULT_MSG_1005			= "IO 오류";												// IOException
	public static final String RESULT_MSG_1006			= "파싱 오류";											// ParseException
	public static final String RESULT_MSG_1007			= "매핑 오류";											// MappingException
	public static final String RESULT_MSG_1008			= "Method Type 오류";									// HttpRequestMethodNotSupportedException, HttpMessageNotReadableException
	public static final String RESULT_MSG_1009			= "접근권한 오류";										// IllegalAccessException

	/* Result Message :: 공통(Custom) */
	public static final String RESULT_MSG_1101			= "%s 토큰 입니다.";										// JwtTokenException
	public static final String RESULT_MSG_1102			= "파라미터 %s 값이 없습니다.";								// NotDataException
	public static final String RESULT_MSG_1103			= "파라미터 %s 값이 지정된 형식에 맞지 않습니다.";				// BadDataException

	/* Result Message :: 회원 관련 */
	public static final String RESULT_MSG_2001 			= "회원 정보가 없습니다.";
	public static final String RESULT_MSG_2002 			= "탈퇴한 회원 입니다.";
	public static final String RESULT_MSG_2011 			= "가입이 제한된 ADID 입니다.";
	public static final String RESULT_MSG_2012 			= "가입이 제한된 이메일 입니다.";
	public static final String RESULT_MSG_2013 			= "가입이 제한된 핸드폰 입니다.";
	public static final String RESULT_MSG_2021 			= "이미 구글로 가입한 회원 정보가 있습니다.";
	public static final String RESULT_MSG_2022 			= "이미 페이스북으로 가입한 회원 정보가 있습니다.";
	public static final String RESULT_MSG_2023 			= "이미 카카오로 가입한 회원 정보가 있습니다.";
	public static final String RESULT_MSG_2024 			= "이미 네이버로 가입한 회원 정보가 있습니다.";
	public static final String RESULT_MSG_2031 			= "회원만 이용할 수 있습니다.";

	/* Result Message :: 포인트 관련 */
	public static final String RESULT_MSG_3001 			= "포인트 적립이 실패하였습니다.";
	public static final String RESULT_MSG_3002 			= "이미 포인트를 적립 받은 콘텐츠에요.";
	public static final String RESULT_MSG_3003 			= "API 포인트 적립이 실패하였습니다.";
	public static final String RESULT_MSG_3004 			= "일일 적립 가능한 최대 포인트를 초과했습니다.";
	public static final String RESULT_MSG_3005 			= "잘못 된 박스번호 입니다.";
	public static final String RESULT_MSG_3006 			= "다시 시도해 주세요.";
	public static final String RESULT_MSG_3007 			= "스케줄 업데이트 데이터";

	/* Result Message :: 오퍼월 관련 */
	public static final String RESULT_MSG_5001 			= "오퍼월 적립이 실패하였습니다.";
	public static final String RESULT_MSG_5002 			= "허용되지 않은 IP입니다.";
	public static final String RESULT_MSG_5003 			= "중복참여로 적립이 불가합니다.";
	public static final String RESULT_MSG_5004 			= "API 오퍼월 적립이 실패하였습니다.";
	public static final String RESULT_MSG_5005 			= "포미션 url 정보가 없습니다.";

	public static final String RESULT_MSG_9999			= "에러코드 미정의";


	public static final String RESULT_OK_CODE 										= "0";
	public static final String RESULT_OK_MSG 										= "성공";

	public static final String RESULT_TOKEN_ERROR_CODE 								= "1";
	public static final String RESULT_TOKEN_ERROR_MSG 								= "토큰 에러 입니다.";

	public static final String RESULT_QUERY_ERROR_CODE 								= "2";
	public static final String RESULT_QUERY_ERROR_MSG 								= "DB QUERY 에러 입니다.";

	public static final String RESULT_NO_DATA_CODE 									= "3";
	public static final String RESULT_NO_DATA_MSG 									= "데이터가 없습니다.";

	public static final String RESULT_INVALID_INPUT_CODE 							= "4";
	public static final String RESULT_INVALID_INPUT_MSG 							= "잘못된 데이터 입니다.";

	public static final String RESULT_EXIST_GUID_CODE 								= "5";
	public static final String RESULT_EXIST_GUID_MSG 								= "이미 등록된 GUID 데이터가 있습니다.";

	public static final String RESULT_EXIST_EMAIL_CODE 								= "6";
	public static final String RESULT_EXIST_EMAIL_MSG 								= "이미 등록된 이메일 데이터가 있습니다.";

	public static final String RESULT_EXIST_PHONE_CODE 								= "7";
	public static final String RESULT_EXIST_PHONE_MSG 								= "이미 등록된 핸드폰 데이터가 있습니다.";

	public static final String RESULT_EXIST_USER_CODE 								= "8";
	public static final String RESULT_EXIST_USER_MSG 								= "이미 가입된 회원 정보가 있습니다.";

	public static final String RESULT_MISMATCH_EMAIL_PASS_CODE 						= "9";
	public static final String RESULT_MISMATCH_EMAIL_PASS_MSG 						= "이메일이나 비밀번호를 다시 확인해주세요.";

	public static final String RESULT_MISMATCH_RECOMMENDER_CODE 					= "10";
	public static final String RESULT_MISMATCH_RECOMMENDER_MSG 						= "추천인 코드를 다시 확인해주세요.";

	public static final String RESULT_DROP_USER_CODE 								= "11";
	public static final String RESULT_DROP_USER_MSG 								= "탈퇴한 회원 입니다.";

	public static final String RESULT_SMS_VERIFY_TIME_OVER_CODE 					= "12";
	public static final String RESULT_SMS_VERIFY_TIME_OVER_MSG 						= "SMS 인증시간을 초과했습니다.";

	public static final String RESULT_SMS_VERIFY_CODE_MISMATCH_CODE 				= "13";
	public static final String RESULT_SMS_VERIFY_CODE_MISMATCH_MSG 					= "SMS 인증코드가 일치하지 않습니다.";

	public static final String RESULT_EXIST_PHONE_NUMBER_CODE 						= "14";
	public static final String RESULT_EXIST_PHONE_NUMBER_MSG 						= "이미 존재하는 핸드폰 번호 입니다.";

	public static final String RESULT_DAILY_CASH_EXCESS_CODE 						= "15";
	public static final String RESULT_DAILY_CASH_EXCESS_MSG 						= "일일 최대 적립 캐시를 초과했습니다.";


	public static String getCode(Object code) {
		String result;
		try {
			result = ResultCode.class.getField("RESULT_CODE_"+code).toString();
		} catch (IllegalArgumentException | NoSuchFieldException | SecurityException e) {
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
