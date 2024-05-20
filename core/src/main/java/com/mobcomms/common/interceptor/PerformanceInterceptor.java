package com.mobcomms.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
@RequiredArgsConstructor
public class PerformanceInterceptor implements HandlerInterceptor {
    private static final Marker MARKER_PERFORMANCE_TIME = MarkerFactory.getMarker("performanceTime");
    private static final Marker MARKER_LATENCY_TIME = MarkerFactory.getMarker("latencyTime");
    private long startTime;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        startTime = System.currentTimeMillis();
        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        long executeTimes = System.currentTimeMillis() - startTime;

        if(executeTimes > 1000) {
            log.info(MARKER_LATENCY_TIME, "[Call '{}'] execute times - {}ms", request.getRequestURI(), executeTimes);
        } else  {
            log.info(MARKER_PERFORMANCE_TIME, "[Call '{}'] execute times - {}ms", request.getRequestURI(), executeTimes);
        }
    }
}
