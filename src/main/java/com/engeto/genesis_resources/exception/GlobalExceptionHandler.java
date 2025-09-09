package com.engeto.genesis_resources.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import jakarta.validation.ConstraintViolationException;
import java.util.stream.Collectors;

import java.time.Instant;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatus status, String message, String path) {
        Map<String, Object> body = Map.of(
                "timestamp", Instant.now(),
                "status", status.value(),
                "error", status.getReasonPhrase(),
                "message", message,
                "path", path
        );
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler({UserNotFoundException.class, InvalidPersonIdException.class, DatabaseConstraintException.class})
    public ResponseEntity<Map<String, Object>> handleCustomExceptions(RuntimeException e, HttpServletRequest request) {
        HttpStatus status;

        if (e instanceof UserNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        } else if (e instanceof InvalidPersonIdException || e instanceof DatabaseConstraintException) {
            status = HttpStatus.BAD_REQUEST;
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return buildErrorResponse(status, e.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
            ConstraintViolationException e, HttpServletRequest request) {

        String message = e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("; "));

        return buildErrorResponse(HttpStatus.BAD_REQUEST, message, request.getRequestURI());
    }

    // Catch-all for other unhandled RuntimeExceptions
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleOtherRuntimeExceptions(RuntimeException e, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), request.getRequestURI());
    }
}
