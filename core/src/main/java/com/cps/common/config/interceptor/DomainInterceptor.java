package com.cps.common.config.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/*
 * Created by enliple
 * Create Date : 2024-07-03
 * Class 설명, method
 * UpdateDate : 2024-07-03, 업데이트 내용
 */
@Component
public class DomainInterceptor implements HandlerInterceptor {

    private static final String ALLOWED_DOMAIN = "allowed-domain.com";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String host = request.getHeader("Host");
        String remoteAddr = request.getRemoteAddr();
        System.out.println("Client IP: " + remoteAddr);
        String remoteHost = request.getRemoteHost();
        System.out.println("Client Host: " + remoteHost);
        String hostHeader = request.getHeader("Host");
        System.out.println("Host Header: " + hostHeader);
        if (host != null && host.endsWith(ALLOWED_DOMAIN)) {

        } else {
            //response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
            //return false;
        }

        return true;
    }
}