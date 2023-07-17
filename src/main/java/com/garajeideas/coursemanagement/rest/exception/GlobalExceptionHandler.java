package com.garajeideas.coursemanagement.rest.exception;

import com.garajeideas.coursemanagement.dtos.ErrorDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.zalando.problem.AbstractThrowableProblem;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String[] codes = error.getCodes();
            String errorMessage = "";
            if (codes != null) {
                errorMessage = codes[codes.length - 1];
            }
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler({CourseNotFoundException.class, CourseNotDeletedException.class,
            CourseStudentDuplicityException.class, CourseStudentNotFoundException.class,
            StudentNotFoundException.class, DuplicateCourseException.class, CourseFullException.class,
            CourseSizeException.class, DuplicateStudentException.class})
    public ResponseEntity<Object> handleCourseNotFoundException(AbstractThrowableProblem ex) {
        ErrorDetails errorResponse = new ErrorDetails(
                ex.getTitle(),
                ex.getDetail(),
                Objects.requireNonNull(ex.getStatus()).getStatusCode(),
                ZonedDateTime.now(ZoneOffset.UTC)
        );

        return ResponseEntity.status(ex.getStatus().getStatusCode()).body(errorResponse);
    }
}