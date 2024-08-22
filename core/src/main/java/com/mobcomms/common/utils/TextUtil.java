package com.mobcomms.common.utils;

import org.springframework.util.MultiValueMap;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 텍스트 관련 Utility 모음
 * history
 * 。
 * 。
 */
public class TextUtil {

	//private static Log log = LogFactory.getLog(TextUtil.class);

	/**
	 * 문자열을 지정된 Token list_seperator로 Tokenize한다.<br>
	 * <code>
	 *  String source = "Text token\tis A Good\nAnd bad.";<br>
	 *  String[] result = TextUtil.split(source, " \t\n");<br>
	 *  </code> <code>result</code>는
	 * <code>"Text","token","is","A","Good","And","bad."</code> 를 가지게 된다.
	 * 
	 * @param str
	 *            원본 문자열
	 * @param pattern
	 *            Token list_seperators
	 * @return as 토큰들의 배열
	 */
	public static String[] split(String str, String pattern) throws Exception {

		if (str == null) {
			return null;
		}

		StringTokenizer strToken = null;
		String[] as = null;

		try {
			strToken = new StringTokenizer(str, pattern);
			int i = strToken.countTokens();
			as = new String[i];
			for (int j = 0; j < i; j++) {
				as[j] = strToken.nextToken();
			}
		} catch (Exception e) {
			// log.error("split()" + e);
			throw e;
		}
		return as;
	}

	/**
	 * 문자열 좌우 공백/탭/개행문자 제거
	 * 
	 * @param str
	 *            str 문자열 값
	 * @return String str 좌우 공백/탭/개행문자 가 제거된 문자열 값
	 * @throws Exception
	 */
	public static String trim(String str) throws Exception {

		if (str == null)
			return "";
		int st = 0;
		int count = 0;
		int len = 0;
		try {
			char[] val = str.toCharArray();
			count = val.length;
			len = count;
			while ((st < len)
					&& ((val[st] <= ' ') || (val[st] == ' ')
							|| (val[st] == '\r') || (val[st] == '\n')))
				st++;
			while ((st < len)
					&& ((val[len - 1] <= ' ') || (val[len - 1] == ' ')))
				len--;
		} catch (Exception e) {

			throw e;
		}
		return ((st > 0) || (len < count)) ? str.substring(st, len) : str;
	}

	/**
	 * 문자열 값이 null 이면 "", 아니면 원 문자열 값을 반환한다.
	 * 
	 * @param str
	 *            str 문자열 값
	 * @return String str null 이면 "", 아니면 원 문자열 값
	 * @throws Exception
	 */
	/*
	public static String isNull(String str) throws Exception {

		return (str==null) ? "" : str;
	}*/

	/**
	 * 문자열 값이 null 이면 val, 아니면 원 문자열 값을 반환한다.
	 * 
	 * @param str
	 *            str 문자열 값
	 * @param str
	 *            val 문자열 값이 null 일경우 반환될 문자열 값
	 * @return String str null 이면 val, 아니면 원 문자열 값
	 * @throws Exception
	 */
	public static String isNull(String str, String val) throws Exception {
		if( "null".equals(str) || "NULL".equals(str) ) {
			return val;
		}
		
		return (str == null || "".equals(str)) ? val : str;
	}
	
	/**
	 */
	public static boolean isNull(String str) {
		if( "null".equals(str) || "NULL".equals(str) ) {
			return true;
		}
		
		return (str == null || "".equals(str)) ? true : false;
	}
	
	
	
	public static String checkIntegerStr(String value) {
		if( isNull(value) ) {
			return "0";
		}
		
		try {
			double dValue = Double.parseDouble( value );
			long iValue = Math.round(dValue);
			return iValue+"";
		} catch (Exception e) {
		}
		
		return "0";
	}
	
	
	
	
	public static int parseInt(Object value) {
		if( value==null ) {
			return 0;
		}
		
		String valueStr = String.valueOf( value );
		if( isNull(valueStr) ) {
			return 0;
		}
		
		try {
			double dValue = Double.parseDouble( valueStr );
			long iValue = Math.round(dValue);
			return (int)iValue;
		} catch (Exception e) {
		}
		
		return 0;
	}
	
	
	
	public static double parseDouble(Object value) {
		if( value==null ) {
			return 0.0;
		}
		
		String valueStr = String.valueOf( value );
		if( isNull(valueStr) ) {
			return 0.0;
		}
		
		try {
			double dValue = Double.parseDouble( valueStr );
			return dValue;
		} catch (Exception e) {
		}
		
		return 0;
	}
	
	

	/**
	 * num 값에 len 자리수 만큼 '0' 값을 붙힌 문자열 값을 반환한다.
	 * 
	 * @param num
	 *            num 정수 값
	 * @param len
	 *            len 반환될 문자열 수
	 * @return String str len 자리수 만큼 '0' 값을 붙힌 문자열
	 * @throws Exception
	 */
	public static String lpad(int num, int len) throws Exception {

		return lpad(String.valueOf(num), len, '0');
	}

	/**
	 * str 값에 len 자리수 만큼 '0' 값을 붙힌 문자열 값을 반환한다.
	 * 
	 * @param str
	 *            str 원본 문자열
	 * @param len
	 *            len 반환될 문자열 수
	 * @return String str len 자리수 만큼 '0' 값을 붙힌 문자열
	 * @throws Exception
	 */
	public static String lpad(String str, int len) throws Exception {

		return lpad(str, len, '0');
	}

	/**
	 * str 값에 len 자리수 만큼 c 값을 붙힌 문자열 값을 반환한다.
	 * 
	 * @param str
	 *            str 원본 문자열
	 * @param len
	 *            len 반환될 문자열 수
	 * @param c
	 *            c 추가할 문자 값
	 * @return String str len 자리수 만큼 c 값을 붙힌 문자열
	 * @throws Exception
	 */
	public static String lpad(String str, int len, char c) throws Exception {
		if (str == null)
			return "";
		if (len < 0)
			return str;
		StringBuffer buf = null;
		try {
			if (str.length() > len)
				return str.substring(str.length() - len);

			buf = new StringBuffer();
			for (int i = 0; i < len - str.length(); i++) {
				buf.append(c);
			}
			buf.append(str);
		} catch (Exception e) {
			// log.error("lpad()" + e);
			throw e;
		}
		return buf.toString();
	}

	/**
	 * String을 자리수만큼 특정 문자로 채워줌 사용함
	 * 
	 * @param str
	 *            str 검증 대상 파라미터 값
	 * @return String str 유효한 파라미터 값
	 * @throws Exception
	 */
	public static String lpad(String str, int len, String c) throws Exception {
		if (str == null)
			return "";
		if (len < 0)
			return str;
		StringBuffer buf = null;
		try {
			if (str.length() > len)
				return str.substring(str.length() - len);

			buf = new StringBuffer();
			for (int i = 0; i < len - str.length(); i++) {
				buf.append(c);
			}
			buf.append(str);
		} catch (Exception e) {
			// log.error("lpad()" + e);
			throw e;
		}
		return buf.toString();
	}

	/**
	 * String을 자리수만큼 특정 문자로 채워줌 사용함
	 * 
	 * @param str
	 *            str 검증 대상 파라미터 값
	 * @return String str 유효한 파라미터 값
	 * @throws Exception
	 */
	public static String rpad(String str, int len, String c) throws Exception {
		if (str == null)
			return "";
		if (len < 0)
			return str;
		StringBuffer buf = null;
		try {
			if (str.length() > len)
				return str.substring(str.length() - len);

			buf = new StringBuffer();
			buf.append(str);
			for (int i = 0; i < len - str.length(); i++) {
				buf.append(c);
			}

		} catch (Exception e) {
			// log.error("lpad()" + e);
			throw e;
		}
		return buf.toString();
	}

	/**
	 * 한글혼용 문자열을 주어진 길이만큼 자르는 method이다.
	 * 
	 * @param s :
	 *            문자열
	 * @param len :
	 *            제한 길이
	 * @param tail :
	 *            끝에 붙이고자 하는 문자열
	 * @return
	 */
	public static String truncateNicely(String s, int len, String tail) throws Exception {

		if (s == null)
			return "";

		int srcLen = getRealLength(s);

		if (srcLen < len) {
			return s;
		}

		String tmpTail = (tail == null) ? "" : tail;

		int tailLen = getRealLength(tmpTail);

		if (tailLen > len) {
			return "";
		}

		char a;
		int i = 0;
		int realLen = 0;
		for (i = 0; i < len - tailLen && realLen < len - tailLen; i++) {
			a = s.charAt(i);

			realLen += ((a & 0xFF00) == 0) ? 1 : 2;
		}

		while (getRealLength(s.substring(0, i)) > len - tailLen) {
			i--;
		}

		return s.substring(0, i) + tmpTail;
	}

	/**
	 * 문자열에 대한 실바이트수를 반환하는 method이다.
	 * 
	 * @param str :
	 *            문자열
	 * @return
	 */
	public static int getRealLength(String str) throws Exception {

		if (str == null)
			return 0;
		return str.getBytes().length;
	}
	
	
	/**
	 * HTML의 입력상자 (&ltINPUT&gt, &ltTEXTAREA&gt)에서 원래의 String 형태 그대로 보여지도록 바꿔주는 메소드<br>
	 * db query 시 데이타 특수문자 처리
	 * @param strIn - 입력상자에서도 그대로 보여지기를 원하는 String
	 * @return 형태 그대로 입력상자에서 보여지도록 바뀐 최종 String
	 */
	public static String toHtmlInputText(String strIn) throws Exception {
		String str = strIn;

		str = str.replaceAll("&", "&amp;");
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		str = str.replaceAll("'", "&acute;");
		str = str.replaceAll("\"", "&quot;");

		return str;
	}	
	
	/**
	 * DB2에서 J Mode 케릭세셑 변경처리 함수<br>
	 * db query 시 데이타 특수문자 처리
	 * @param strIn - 2Byte 문자를 포함한 String
	 * @return 1Byte 문자열로 바뀐 String
	 */
	public static String convertTwoOneByte(String strIn) {
		String str = strIn==null?"":strIn;

		str = str.replaceAll("　", " ");
		str = str.replaceAll("Ａ", "A");
		str = str.replaceAll("Ｂ", "B");
		str = str.replaceAll("Ｃ", "C");
		str = str.replaceAll("Ｄ", "D");
		str = str.replaceAll("Ｅ", "E");
		str = str.replaceAll("Ｆ", "F");
		str = str.replaceAll("Ｇ", "G");
		str = str.replaceAll("Ｈ", "H");
		str = str.replaceAll("Ｉ", "I");
		str = str.replaceAll("Ｊ", "J");
		str = str.replaceAll("Ｋ", "K");
		str = str.replaceAll("Ｌ", "L");
		str = str.replaceAll("Ｍ", "M");
		str = str.replaceAll("Ｎ", "N");
		str = str.replaceAll("Ｏ", "O");
		str = str.replaceAll("Ｐ", "P");
		str = str.replaceAll("Ｑ", "Q");
		str = str.replaceAll("Ｒ", "R");
		str = str.replaceAll("Ｓ", "S");
		str = str.replaceAll("Ｔ", "T");
		str = str.replaceAll("Ｕ", "U");
		str = str.replaceAll("Ｖ", "V");
		str = str.replaceAll("Ｗ", "W");
		str = str.replaceAll("Ｘ", "X");
		str = str.replaceAll("Ｙ", "Y");
		str = str.replaceAll("Ｚ", "Z");
		str = str.replaceAll("１", "1");
		str = str.replaceAll("２", "2");
		str = str.replaceAll("３", "3");
		str = str.replaceAll("４", "4");
		str = str.replaceAll("５", "5");
		str = str.replaceAll("６", "6");
		str = str.replaceAll("７", "7");
		str = str.replaceAll("８", "8");
		str = str.replaceAll("９", "9");
		str = str.replaceAll("０", "0");
		str = str.replaceAll("ａ", "a");
		str = str.replaceAll("ｂ", "b");
		str = str.replaceAll("ｃ", "c");
		str = str.replaceAll("ｄ", "d");
		str = str.replaceAll("ｅ", "e");
		str = str.replaceAll("ｆ", "f");
		str = str.replaceAll("ｇ", "g");
		str = str.replaceAll("ｈ", "h");
		str = str.replaceAll("ｉ", "i");
		str = str.replaceAll("ｊ", "j");
		str = str.replaceAll("ｋ", "k");
		str = str.replaceAll("ｌ", "l");
		str = str.replaceAll("ｍ", "m");
		str = str.replaceAll("ｎ", "n");
		str = str.replaceAll("ｏ", "o");
		str = str.replaceAll("ｐ", "p");
		str = str.replaceAll("ｑ", "q");
		str = str.replaceAll("ｒ", "r");
		str = str.replaceAll("ｓ", "s");
		str = str.replaceAll("ｔ", "t");
		str = str.replaceAll("ｕ", "u");
		str = str.replaceAll("ｖ", "v");
		str = str.replaceAll("ｗ", "w");
		str = str.replaceAll("ｘ", "x");
		str = str.replaceAll("ｙ", "y");
		str = str.replaceAll("ｚ", "z");
		str = str.replaceAll("！", "!");
		str = str.replaceAll("＠", "@");
		str = str.replaceAll("＃", "#");
		str = str.replaceAll("＄", "$");
		str = str.replaceAll("％", "%");
		str = str.replaceAll("＾", "^");
		str = str.replaceAll("＆", "&");
		str = str.replaceAll("＊", "*");
		str = str.replaceAll("（", "(");
		str = str.replaceAll("）", ")");
		str = str.replaceAll("＿", "_");
		str = str.replaceAll("＋", "+");
		str = str.replaceAll("－", "-");
		str = str.replaceAll("＝", "=");
		str = str.replaceAll("［", "[");
		str = str.replaceAll("］", "]");
		str = str.replaceAll("｛", "{");
		str = str.replaceAll("｝", "}");
		str = str.replaceAll("；", ";");
		str = str.replaceAll("＇", "'");
		str = str.replaceAll("：", ":");
		str = str.replaceAll("＂", "\"");
		str = str.replaceAll("，", ",");
		str = str.replaceAll("．", ".");
		str = str.replaceAll("／", "/");
		str = str.replaceAll("＜", "<");
		str = str.replaceAll("＞", ">");
		str = str.replaceAll("？", "?");
		str = str.replaceAll("｀", "`");
		str = str.replaceAll("～", "~");

		return str;
	}

	/**
	 * DB2에서 J Mode 케릭세셑 변경처리 함수<br>
	 * db query 시 데이타 특수문자 처리
	 * @param strIn - 2Byte 문자를 포함한 String
	 * @return 1Byte 문자열로 바뀐 String
	 */
	public static String convertOneTwoByte(String strIn) {
		String str = strIn==null?"":strIn;

		str = str.replaceAll(" ", "　");
		str = str.replaceAll("A", "Ａ");
		str = str.replaceAll("B", "Ｂ");
		str = str.replaceAll("C", "Ｃ");
		str = str.replaceAll("D", "Ｄ");
		str = str.replaceAll("E", "Ｅ");
		str = str.replaceAll("F", "Ｆ");
		str = str.replaceAll("G", "Ｇ");
		str = str.replaceAll("H", "Ｈ");
		str = str.replaceAll("I", "Ｉ");
		str = str.replaceAll("J", "Ｊ");
		str = str.replaceAll("K", "Ｋ");
		str = str.replaceAll("L", "Ｌ");
		str = str.replaceAll("M", "Ｍ");
		str = str.replaceAll("N", "Ｎ");
		str = str.replaceAll("O", "Ｏ");
		str = str.replaceAll("P", "Ｐ");
		str = str.replaceAll("Q", "Ｑ");
		str = str.replaceAll("R", "Ｒ");
		str = str.replaceAll("S", "Ｓ");
		str = str.replaceAll("T", "Ｔ");
		str = str.replaceAll("U", "Ｕ");
		str = str.replaceAll("V", "Ｖ");
		str = str.replaceAll("W", "Ｗ");
		str = str.replaceAll("X", "Ｘ");
		str = str.replaceAll("Y", "Ｙ");
		str = str.replaceAll("Z", "Ｚ");
		str = str.replaceAll("1", "１");
		str = str.replaceAll("2", "２");
		str = str.replaceAll("3", "３");
		str = str.replaceAll("4", "４");
		str = str.replaceAll("5", "５");
		str = str.replaceAll("6", "６");
		str = str.replaceAll("7", "７");
		str = str.replaceAll("8", "８");
		str = str.replaceAll("9", "９");
		str = str.replaceAll("0", "０");
		str = str.replaceAll("a", "ａ");
		str = str.replaceAll("b", "ｂ");
		str = str.replaceAll("c", "ｃ");
		str = str.replaceAll("d", "ｄ");
		str = str.replaceAll("e", "ｅ");
		str = str.replaceAll("f", "ｆ");
		str = str.replaceAll("g", "ｇ");
		str = str.replaceAll("h", "ｈ");
		str = str.replaceAll("i", "ｉ");
		str = str.replaceAll("j", "ｊ");
		str = str.replaceAll("k", "ｋ");
		str = str.replaceAll("l", "ｌ");
		str = str.replaceAll("m", "ｍ");
		str = str.replaceAll("n", "ｎ");
		str = str.replaceAll("o", "ｏ");
		str = str.replaceAll("p", "ｐ");
		str = str.replaceAll("q", "ｑ");
		str = str.replaceAll("r", "ｒ");
		str = str.replaceAll("s", "ｓ");
		str = str.replaceAll("t", "ｔ");
		str = str.replaceAll("u", "ｕ");
		str = str.replaceAll("v", "ｖ");
		str = str.replaceAll("w", "ｗ");
		str = str.replaceAll("x", "ｘ");
		str = str.replaceAll("y", "ｙ");
		str = str.replaceAll("z", "ｚ");
		str = str.replaceAll("!", "！");
		str = str.replaceAll("@", "＠");
		str = str.replaceAll("#", "＃");
		str = str.replaceAll("\\$", "＄");
		str = str.replaceAll("%", "％");
		str = str.replaceAll("\\^", "＾");
		str = str.replaceAll("&", "＆");
		str = str.replaceAll("\\*", "＊");
		str = str.replaceAll("\\(", "（");
		str = str.replaceAll("\\)", "）");
		str = str.replaceAll("_", "＿");
		str = str.replaceAll("\\+", "＋");
		str = str.replaceAll("\\-", "－");
		str = str.replaceAll("=", "＝");
		str = str.replaceAll("\\[", "［");
		str = str.replaceAll("\\]", "］");
		str = str.replaceAll("\\{", "｛");
		str = str.replaceAll("\\}", "｝");
		str = str.replaceAll(";", "；");
		str = str.replaceAll("'", "＇");
		str = str.replaceAll(":", "：");
		str = str.replaceAll("\\\"", "＂");
		str = str.replaceAll(",", "，");
		str = str.replaceAll("\\.", "．");
		str = str.replaceAll("/", "／");
		str = str.replaceAll("<", "＜");
		str = str.replaceAll(">", "＞");
		str = str.replaceAll("\\?", "？");
		str = str.replaceAll("`", "｀");
		str = str.replaceAll("~", "～");
		return str;
	}	

	 public static String insertComma(String str)
	 {
	  if (str == null||"".equals(str))
	   return "";
	  
	  String bk = "";
	  /*--------------------소수점 아랫자리 존재할때------------*/
	  if(str.indexOf('.')>-1){
	  bk = str.substring(str.indexOf('.')); //str의 소숫점자리
	  str = str.substring(0,str.indexOf('.'));
	  }

	  int lm_iLen = str.length();
	  String lm_sRet = "";
	  
	  if (lm_iLen < 0) return "";
	  
	  if (str.charAt(0) == '-')
	  {
	   str = str.substring(1);
	   lm_iLen -= 1;
	  
	   int size = 3;
	   int cnt = lm_iLen / size;
	   int pos = lm_iLen % size;
	   String result = "";
	  
	   for( int i = cnt ; i > 0 ; i-- )
	   {
	    result = "," + str.substring(pos+(i-1)*size, pos+i*size) + result ;
	   }
	  
	   if( pos == 0 )
	    lm_sRet = "-" + result.substring(1);
	   else
	   lm_sRet = "-" + str.substring(0,pos) + result;
	  }
	  else
	  {
	   int size = 3;
	   int cnt = lm_iLen / size;
	   int pos = lm_iLen % size;
	   String result = "";
	  
	   for( int i = cnt ; i > 0 ; i-- )
	   {
	    result = "," + str.substring(pos+(i-1)*size, pos+i*size) + result ;
	   }
	  
	   if( pos == 0 )
	    lm_sRet = result.substring(1);
	   else
	   lm_sRet = str.substring(0,pos) + result;
	  }
	  
	   if(bk.indexOf('.')>-1){
	    lm_sRet+=bk;
	  }
	  
	  return lm_sRet;
	 }

	public static String nvl(Object obj ) {
		if (obj==null || "null".equals(obj)) {
			return "";
		} else {
			return obj.toString().trim();
		}
	}

	/**
	 * 문자열 포맷 입히기
	 * @param 
	 * @return String 문자열로 바뀐 String
	 */
	public static String formatString(String format, String strIn) {
		String str = strIn==null?"":strIn.trim();
		String strOut = ""; 
		
		if (str.length()==0) return "";
		
		int strInCnt = 0;
		for (int i=0;i<format.length();i++) {
			if (format.substring(i,i+1).equals("#")) {
				strOut += str.substring(strInCnt,strInCnt+1);
				strInCnt++;
			} else {
				strOut += format.substring(i,i+1);
			}
		}
		
		return strOut;
	}
	
	
	/**
	 * 문자열 콤마 찍기
	 * @param 
	 * @return String 문자열로 바뀐 String
	 */
	public static String formatNumberComma(Object string) {
		String strIn = null;
		if( string!=null ) {
			strIn = string.toString();
		}
		String num = strIn==null?"":strIn.trim();
		
		 //Null 체크
        if(num == null || num.isEmpty()) return "0"; 
    
        //숫자형태가 아닌 문자열일경우 디폴트 0으로 반환 
        String numberExpr = "^[-+]?(0|[1-9][0-9]*)(\\.[0-9]+)?([eE][-+]?[0-9]+)?$";
        boolean isNumber = num.matches(numberExpr);
        if (!isNumber) return "0";             
    
        String strResult = num; //출력할 결과를 저장할 변수
        Pattern p = Pattern.compile("(^[+-]?\\d+)(\\d{3})"); //정규표현식 
        Matcher regexMatcher = p.matcher(num); 
        
        int cnt = 0;
        while(regexMatcher.find()) {                
            strResult = regexMatcher.replaceAll("$1,$2"); //치환 : 그룹1 + "," + 그룹2
                
            //치환된 문자열로 다시 matcher객체 얻기 
            //regexMatcher = p.matcher(strResult); 
            regexMatcher.reset(strResult); 
        }        
        return strResult;
	}
	
	public static String converTimeToString(long time) {
		
		long hh = time>360?time/360:0; 
		long mm = time>60?(time-hh*60)/60:0;
		long ss = (time - hh*60 - mm*60);
		return (hh>9?""+hh:"0"+hh) + ":" + (mm>9?""+mm:"0"+mm) + ":" + (ss>9?""+ss:"0"+ss);				
	}
	
	public static String formatTelNoString(String hpno) {

		String result = hpno;
		if (hpno.length()==10) {
			result = hpno.substring(0,3)+"-"+hpno.substring(3,6)+"-"+hpno.substring(6,10);
 		} else if (hpno.length()==11) {
 			result = hpno.substring(0,3)+"-"+hpno.substring(3,7)+"-"+hpno.substring(7,11);
 		}
		
		return result;
	}
	
	
	public static String formatWonDataFormat(String wonData) {

		if( wonData!=null && !"0".equals(wonData) && !"".equals(wonData) && !"null".equals(wonData) ) {
			wonData = formatNumberComma(wonData)+ "원";
		} else {
			wonData = "";
		}
		
		return wonData;
	}
	
	
	public static String formatWonDataFormat(String wonData, boolean defaultValue) {

		if( wonData!=null && !"0".equals(wonData) && !"".equals(wonData) && !"null".equals(wonData) ) {
			wonData = formatNumberComma(wonData)+ "원";
		} else {
			wonData = "0원";
		}
		
		return wonData;
	}
	
	

	public static Map<String,String> formatWonDataMap(Map<String,String> data) {
		if( data == null ) {
			return data;
		}
		
		Map<String,String> returnMap = new HashMap<String,String>();
		for (Map.Entry<String, String> entry: data.entrySet()) {
			try {
				String key = entry.getKey();
				String valueStr = String.valueOf( entry.getValue() );
				returnMap.put(key, valueStr);
				
				int value = Integer.parseInt(valueStr);
				returnMap.put(key+"_FORMAT", formatWonDataFormat(value));
			} catch (Exception e) {
			}
		}
		
		return returnMap;
	}
	
	
	
	public static String formatWonDataFormat(int wonData) {
		
		return formatWonDataFormat(wonData+"");
	}
	
	
	

	public static String formatWonDataFormat(String wonData, String chr) {

		if( wonData!=null && !"0".equals(wonData) && !"".equals(wonData) && !"null".equals(wonData) ) {
			wonData = formatNumberComma(wonData)+ "원";
		} else {
			wonData = chr;
		}
		
		return wonData;
	}
	
	
	/**
	 * 엑셀 데이터 날짜포맷을 20130413 String 형식으로 리턴
	 * @param yyymmdd
	 * @return
	 */
	public static String excelFormatDateToString( String yyymmdd ) {
		if( yyymmdd!=null || !yyymmdd.equals("") ) {
			try {
				yyymmdd = yyymmdd.replaceAll("\\p{Space}","");
				String k = "";
				if( yyymmdd.lastIndexOf(".") != -1 ) { k = "."; }
				if( yyymmdd.lastIndexOf("-") != -1 ) { k = "-"; }
				if( yyymmdd.lastIndexOf("/") != -1 ) { k = "/"; }
				
				String [] sp = yyymmdd.split("[" +k+"]");
				if( sp.length >= 3 ) {
					String year  = sp[0];
					String month = sp[1];
					String day   = sp[2];
					
					if( k.equals("/") ) {
						year  = sp[2];
						month = sp[0];
						day   = sp[1];
					}
					
					if( year.length() == 2 ) {
						year = "20" + year;
					}
					if( month.length() == 1 ) {
						month = "0" + month;
					}
					if( day.length() == 1 ) {
						day = "0" + day;
					}
					yyymmdd = year + month + day;
				} else {
					yyymmdd = yyymmdd.replaceAll("-", "");
					yyymmdd = yyymmdd.replaceAll(".", "");
					yyymmdd = yyymmdd.replaceAll("/", "");
				}
				
				if( yyymmdd.length() != 8 ) {
					yyymmdd = null;
				}
			} catch (Exception e) {
				yyymmdd = null;
			}
		}
		
		return yyymmdd;
	}
	
	
	/**
	 *  문자열 자르기
	 * @param szText
	 * @param nLength
	 * @return
	 */
	public static String cutString(String szText, int nLength) {
		String r_val = szText;
		int oF = 0, oL = 0, rF = 0, rL = 0;
		int nLengthPrev = 0;
		try {
			byte[] bytes = r_val.getBytes("UTF-8"); // 바이트로 보관
			// x부터 y길이만큼 잘라낸다. 한글안깨지게.
			int j = 0;
			if (nLengthPrev > 0)
				while (j < bytes.length) {
					if ((bytes[j] & 0x80) != 0) {
						oF += 2;
						rF += 3;
						if (oF + 2 > nLengthPrev) {
							break;
						}
						j += 3;
					} else {
						if (oF + 1 > nLengthPrev) {
							break;
						}
						++oF;
						++rF;
						++j;
					}
				}
			j = rF;
			while (j < bytes.length) {
				if ((bytes[j] & 0x80) != 0) {
					if (oL + 2 > nLength) {
						break;
					}
					oL += 2;
					rL += 3;
					j += 3;
				} else {
					if (oL + 1 > nLength) {
						break;
					}
					++oL;
					++rL;
					++j;
				}
			}
			r_val = new String(bytes, rF, rL, "UTF-8"); // charset 옵션
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return r_val;
	}
	
	
	/**
	* String이 알파벳과 숫자로만 이루어졌는지 체크한다
	* @param str String
	* @return boolean
	*/
	public static boolean isAlphaNumeic(String str){
	 
	     if(str == null) return false;
	     char[] ch = str.toCharArray();
	     for( char c : ch ){
	         if(!(( c >= '0' && c <= '9')||( c >= 'a' && c <= 'z')||( c >= 'A' && c <= 'Z'))){
	             return false;
	          }
	     }
	     return true;
	}
	
	
	/**
	* 에러 메세지 출력
	*//*
	public static void printError(Exception e){
		System.out.println( "\n\n\n###error... " + DateTime.getCurrDateTime() + "\n" + e.getMessage() + "\n\n\n" );
	}
	public static void printError(HttpServletRequest request, Exception e){
		String url = request.getRequestURI();
		System.out.println( 
			"\n\n\n###error... " + DateTime.getCurrDateTime() + "\n"  +
			"url: " + url + "\n"  + 
			e.getMessage() + "\n\n\n" 
		);
	}*/
	
	
	/**
	  * 정규식 패턴 검증
	  * @param pattern
	  * @param str
	  * @return
	  */
//	 public static boolean checkPattern(String pattern, String str){
//		 boolean okPattern = false;
//		 String regex = null;
//		 
//		 pattern = pattern.trim();
//		 
//		 //숫자 체크
//		 if(StringUtils.equals("num", pattern)){
//		  regex = "^[0-9]*$";
//		 }
//		 
//		 //영문 체크
//		 
//		 //이메일 체크
//		 if(StringUtils.equals("email", pattern)){
//		  regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
//		 }
//		 
//		 //전화번호 체크
//		 if(StringUtils.equals("tel", pattern)){
//		  regex = "^\\d{2,3}-\\d{3,4}-\\d{4}$";
//		 }
//		 
//		 //휴대폰번호 체크
//		 if(StringUtils.equals("phone", pattern)){
//		  regex = "^01(?:0|1[6-9])(?:\\d{3}|\\d{4})\\d{4}$";
//		 }
//		 
//		 okPattern = Pattern.matches(regex, str);
//		 return okPattern;
//	 }
	 
	 
	 
	/**
	 * 비밀번호 문자 + 숫자 조합 검증
	 */
	public static boolean checkPassword(String str){
		boolean checkAlpha  = false;
		boolean checkNumeic = false;
		
		if(str == null) return false;
	    char[] ch = str.toCharArray();
	    for( char c : ch ) {
	    	if( (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
	    		checkAlpha = true;
	        }
	    	if(c >= '0' && c <= '9'){
	    		checkNumeic = true;
	        }
	    }
	    
	    if( checkAlpha && checkNumeic ) {
	    	return true;
	    } else {
	    	return false;
	    }
	}
	
	
	/**
	 * 숫자를 제외한 모든 문자를 제거하고 숫자문자열만 리턴한다.
	 * @return
	 */
	public static String removeCharExceptNumber(String str) {
		return str.replaceAll("[^0-9]", "");
	      
	    // 또는
	    // return str.replaceAll("[^\\d]", "");
	     
	    // 또는
	    // return str.replaceAll("\\D", "");
	     
	    // 모두 사용가능하다.
	}
	
	
	/**
	 * 문자열이 숫자인지 확인한다.
	 * @return
	 */
	public static boolean isNumber(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}

	public static Map<String, String> multiValueMapToMap(MultiValueMap<String, String> multiValueMap) {
		Map<String, String> map = new HashMap<>();
		for (Map.Entry<String, List<String>> entry : multiValueMap.entrySet()) {
			String key = entry.getKey();
			List<String> values = entry.getValue();
			if (values != null && !values.isEmpty()) {
				map.put(key, values.get(0));
			}
		}
		return map;
	}
}
