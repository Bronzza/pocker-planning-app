package com.srepinet.pockerplanningapp.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleForbidden(IllegalArgumentException ex, WebRequest request) {
        log.info("Operation not allowed request= {}, error = {}", request.getDescription(true), ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List> handleMethodArgumentException(MethodArgumentNotValidException exception) {
        ArrayList<String> errors = new ArrayList<>(exception.getBindingResult().getAllErrors().size());

        List<FieldError> fieldErrors = exception.getBindingResult().getAllErrors()
                .stream()
                .map(FieldError.class::cast)
                .collect(Collectors.toList());

        fieldErrors.forEach(fieldError -> {
            errors.add(String.format("Bad request: %s: %s: Rejected value: %s", fieldError.getField(),
                    fieldError.getDefaultMessage(), fieldError.getRejectedValue()));
        });
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }
}
