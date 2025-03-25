package com.sii.backend.exception;

import com.sii.backend.dto.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final String GENERIC_ERROR_MESSAGE = "something went wrong";
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleGlobalException (Exception exception) {
        return new ErrorMessage(GENERIC_ERROR_MESSAGE);
    }
}
