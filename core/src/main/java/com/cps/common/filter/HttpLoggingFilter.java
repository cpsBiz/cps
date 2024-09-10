package com.cps.common.filter;

import com.cps.common.model.HttpLog;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.RequestFacade;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

/*
 * Created by enliple
 * Create Date : 2024-07-10
 * Class 설명, Request, Response 로깅 필터
 * UpdateDate : 2024-07-10, 업데이트 내용
 */
@Slf4j
@Component
public class HttpLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if(isAsyncDispatch(request)){
            filterChain.doFilter(request, response);
        }
        else {
            var cachingRequestWrapper = new ContentCachingRequestWrapper(request);
            var cachingResponseWrapper = new ContentCachingResponseWrapper(response);
            var  startTime = System.currentTimeMillis();
            filterChain.doFilter(cachingRequestWrapper, cachingResponseWrapper);
            var endTime = System.currentTimeMillis();

            if(request instanceof RequestFacade){
                var httpLog = HttpLog.createHttpLog(cachingRequestWrapper, cachingResponseWrapper, endTime - startTime);
                log.info(httpLog.toPrettierLog());
            }
            cachingResponseWrapper.copyBodyToResponse();
        }
    }
}
