package com.cps.rewardLinkPrice.slack;

import com.cps.common.slack.SlackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class Slack {

    private final SlackService slackService;

    @AfterThrowing(pointcut = "execution(* com.cps.common.service..*(..))", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Exception ex) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        String errorMessage = String.format("링크피라이스 리워드 " +className+ "." + methodName + " 서비스 오류 발생: %s", ex.getMessage());
        slackService.sendSlackMessage(errorMessage);
    }
}
