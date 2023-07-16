package com.garajeideas.coursemanagement.rest.validator;

import com.garajeideas.coursemanagement.openapi.web.rest.dtos.CourseRequest;

import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

@ControllerAdvice
public class CourseRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return CourseRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CourseRequest courseRequest = (CourseRequest) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "The course name is required");
        if (courseRequest.getName().length() < 3) {
            errors.rejectValue("name", "The course name must be at least 3 characters long");
        }

        if (courseRequest.getMaxStudentCount() == null) {
            errors.rejectValue("maxStudentCount", "The maximum number of students is required");
        } else if (courseRequest.getMaxStudentCount() < 1) {
            errors.rejectValue("maxStudentCount", "The maximum number of students must be greater than 0");
        }

        if (courseRequest.getStartDate() == null) {
            errors.rejectValue("startDate", "The start date is required");
        } else if (OffsetDateTime.now(ZoneOffset.UTC).toInstant().isAfter(courseRequest.getStartDate().toInstant())) {
            errors.rejectValue("startDate", "The start date must be equal to or later than the current date");
        }

        if (courseRequest.getEndDate() == null) {
            errors.rejectValue("endDate", "The end date is required");
        } else if (courseRequest.getEndDate().isBefore(courseRequest.getStartDate())) {
            errors.rejectValue("endDate", "The end date must be after the start date");
        }

        if (courseRequest.getRegistrationDate() == null) {
            errors.rejectValue("registrationDate", "The registration date is required");
        }
    }
}