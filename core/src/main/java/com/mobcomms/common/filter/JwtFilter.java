package com.mobcomms.common.filter;

import com.mobcomms.common.utils.JWTUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtFilter implements Filter {

    @Value("${jwt.secret}")
    private String secretKey;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("!!!!!");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authorizationHeader = httpRequest.getHeader("Authorization");

        JWTUtils jwtUtils = new JWTUtils(secretKey);

       System.out.println("authorizationHeader : " + authorizationHeader);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            if (jwtUtils.validateToken(token)) {
                String username = jwtUtils.extractUsername(token);
                request.setAttribute("username", username);
            } else {
                throw new ServletException("Invalid token");
            }
        } else {
            throw new ServletException("Missing or invalid Authorization header");
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}