package com.garajeideas.coursemanagement.rest.exception;

import com.garajeideas.coursemanagement.dtos.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler  {

  /*  @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }*/

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String[] codes = error.getCodes();
            String errorMessage ="";
            if (codes != null ) {
                errorMessage =codes[codes.length - 1];
            }
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<Object> handleCourseNotFoundException(CourseNotFoundException ex) {
        ErrorDetails errorResponse = new ErrorDetails(
                ex.getTitle(),
                ex.getDetail(),
                HttpStatus.NOT_FOUND.value(),
                ZonedDateTime.now(ZoneOffset.UTC)
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}