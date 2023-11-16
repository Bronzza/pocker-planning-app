package com.srepinet.pockerplanningapp.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Void> handleGeneral(Throwable t, WebRequest request) {
        log.error("Failed to process request={}", request.getDescription(true), t);
        return ResponseEntity.internalServerError().build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleResourceNotFound(NoSuchElementException ex, WebRequest request) {
        log.info("Failed to find resource request = {}, error = {}", request.getDescription(true), ex.getMessage());
        return ResponseEntity.status(NOT_FOUND).build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleForbidden(IllegalArgumentException ex, WebRequest request) {
        log.info("Operation not allowed request= {}, error = {}", request.getDescription(true), ex.getMessage());
        return ResponseEntity.status(FORBIDDEN).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentException(MethodArgumentNotValidException ex, WebRequest request) {
        log.info("Operation not allowed request= {}, error = {}", request.getDescription(true), ex.getMessage());
        return ResponseEntity.status(BAD_REQUEST).build();
    }
}
