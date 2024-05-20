package com.mobcomms.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({Exception.class})
    public void handleException(Exception e) throws Exception {
        log.error(e.getMessage());
    }
}

