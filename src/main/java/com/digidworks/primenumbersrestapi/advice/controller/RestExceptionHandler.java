package com.digidworks.primenumbersrestapi.advice.controller;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Exception handler for proper messages and reducing java/spring specific error output.
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        StringBuilder message = new StringBuilder();

        Object paramValue = ex.getValue();
        String paramType = ex.getRequiredType().getSimpleName();

        message.append("Received invalid value of \"").append(paramValue).append("\".");

        if (paramType != null) {
            message.append(" Required type: ").append(paramType).append(".");
        }

        return handleExceptionInternal(ex, message, headers, HttpStatus.BAD_REQUEST, request);
    }
}
