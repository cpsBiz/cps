package com.cps.common.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {

    private final ObjectMapper objectMapper;

    @Before("execution(* com.cps..service..*(..))")
    public void logBefore(JoinPoint joinPoint) {
        log.info("====================== PARAM =========================== ");
        log.info("Executing method: " + joinPoint.getSignature().getName());
        Object[] args = joinPoint.getArgs();

        log.info("Argument Count : {}", args.length);
        int i = 0;
        for (Object arg : args) {
            try {
                String json = objectMapper.writeValueAsString(arg);
                log.info("Argument[{}] : {}", ++i, json);
            } catch (JsonProcessingException e) {
                log.error("Could not log argument", e);
            }
        }
        log.info("====================================================== ");
    }

    @AfterReturning(pointcut = "execution(* com.cps..service..*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("===================== RESULT =========================== ");
        log.info("Method executed: " + joinPoint.getSignature().getName());
        try {
            String json = objectMapper.writeValueAsString(result);
            log.info("Return value: " + json);
        } catch (JsonProcessingException e) {
            log.error("Could not log return value", e);
        }
        log.info("======================================================== ");
    }
}
