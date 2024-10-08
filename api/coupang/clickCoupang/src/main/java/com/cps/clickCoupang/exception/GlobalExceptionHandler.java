package com.cps.clickCoupang.exception;

import com.cps.common.model.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldError().getDefaultMessage();
        var result = new BaseResponse();
        result.setError(errorMessage);
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }
}


