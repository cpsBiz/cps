package com.mobcomms.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public HttpStatus handleNotFoundException(NoHandlerFoundException e) {
        log.error("404 Not Found Error: {}", e.getMessage());
        return HttpStatus.NOT_FOUND;
    }

    @ExceptionHandler(Exception.class)
    public HttpStatus handleException(Exception e) {
        log.error("Internal Server Error: {}", e.getMessage());
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}


