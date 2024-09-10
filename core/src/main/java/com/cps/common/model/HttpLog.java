package com.cps.common.model;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.util.Enumeration;

/*
 * Created by enliple
 * Create Date : 2024-07-10
 * Class 설명, Http Log 모델
 * UpdateDate : 2024-07-10, 업데이트 내용
 */
@Data
public class HttpLog {
    private String httpMethod;
    private String requestUrl;
    private HttpStatus httpStatus;
    private String clientIp;
    private String elapsedTime;
    private String requestHeader;
    private String requestBody;
    private String responseBody;

    public static HttpLog createHttpLog( ContentCachingRequestWrapper request, ContentCachingResponseWrapper response, long elapsedTime) {
        HttpLog httpLog = new HttpLog();
        httpLog.setHttpMethod(request.getMethod());
        httpLog.setRequestUrl(request.getRequestURI() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""));
        httpLog.setHttpStatus(HttpStatus.valueOf(response.getStatus()));
        httpLog.setClientIp(request.getRemoteAddr());
        httpLog.setElapsedTime(String.valueOf((elapsedTime)));

        // Headers
        StringBuilder requestHeaders = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();
        requestHeaders.append("Requset Header Info").append(System.lineSeparator());
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            requestHeaders.append("\t").append(headerName).append(": ").append(request.getHeader(headerName)).append(System.lineSeparator());
        }
        httpLog.setRequestHeader(requestHeaders.toString());


        // Request Body
        httpLog.setRequestBody(new String(request.getContentAsByteArray()));

        // Response Body
        httpLog.setResponseBody(new String(response.getContentAsByteArray()));
        return httpLog;
    }

    public String toPrettierLog() {
        StringBuilder sb = new StringBuilder();
        sb.append(System.lineSeparator());
        sb.append("RequestUrl : ").append(requestUrl).append(System.lineSeparator());
        sb.append("HttpMethod : ").append(httpMethod).append(System.lineSeparator());
        sb.append("HttpStatus : ").append(httpStatus).append(System.lineSeparator());
        sb.append("ClientIp : ").append(clientIp).append(System.lineSeparator());
        sb.append("ElapsedTime : ").append(elapsedTime).append(System.lineSeparator());
        sb.append("RequestHeader : ").append(requestHeader).append(System.lineSeparator());
        sb.append("RequestBody : ").append(requestBody).append(System.lineSeparator());
        sb.append("ResponseBody : ").append(responseBody).append(System.lineSeparator());
        return sb.toString();
    }
}
