package com.jv.incidentflow.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(IllegalArgumentException.class)
  ResponseEntity<ApiErrorResponse> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
    return build(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
    var message = ex.getBindingResult().getFieldErrors().stream()
        .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
        .collect(Collectors.joining(", "));
    return build(HttpStatus.BAD_REQUEST, message, request);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  ResponseEntity<ApiErrorResponse> handleConstraint(ConstraintViolationException ex, HttpServletRequest request) {
    return build(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
  }

  private ResponseEntity<ApiErrorResponse> build(HttpStatus status, String message, HttpServletRequest request) {
    return ResponseEntity.status(status).body(new ApiErrorResponse(Instant.now(), status.value(), status.getReasonPhrase(), message, request.getRequestURI()));
  }
}
