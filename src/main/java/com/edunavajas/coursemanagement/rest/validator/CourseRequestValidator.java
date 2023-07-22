package com.edunavajas.coursemanagement.rest.validator;

import com.edunavajas.coursemanagement.openapi.web.rest.dtos.CourseRequest;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@ControllerAdvice
public class CourseRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return CourseRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CourseRequest courseRequest = (CourseRequest) target;

        validateCourseName(courseRequest, errors);
        validateMaxStudentCount(courseRequest, errors);
        validateStartDate(courseRequest, errors);
        validateEndDate(courseRequest, errors);
    }

    private void rejectIfNullOrEmptyOrWhitespace(Object value, String fieldName, String errorMessage, Errors errors) {
        if (value == null || value.toString().trim().isEmpty()) {
            errors.rejectValue(fieldName, errorMessage);
        }
    }

    private void validateCourseName(CourseRequest courseRequest, Errors errors) {
        rejectIfNullOrEmptyOrWhitespace(courseRequest.getName(), "name", "The course name is required", errors);

        if (courseRequest.getName().length() < 3) {
            errors.rejectValue("name", "The course name must have at least 3 characters");
        }
    }

    private void validateMaxStudentCount(CourseRequest courseRequest, Errors errors) {
        rejectIfNullOrEmptyOrWhitespace(courseRequest.getMaxStudentCount(), "maxStudentCount",
                "The maximum number of students is required", errors);

        if (courseRequest.getMaxStudentCount() != null && courseRequest.getMaxStudentCount() < 1) {
            errors.rejectValue("maxStudentCount", "The maximum number of students must be greater than 0");
        }
    }

    private void validateStartDate(CourseRequest courseRequest, Errors errors) {
        rejectIfNullOrEmptyOrWhitespace(courseRequest.getStartDate(), "startDate", "The start date is required", errors);

        if (OffsetDateTime.now(ZoneOffset.UTC).toInstant().isAfter(courseRequest.getStartDate().toInstant())) {
            errors.rejectValue("startDate", "The start date must be later than the current date");
        }
    }

    private void validateEndDate(CourseRequest courseRequest, Errors errors) {
        rejectIfNullOrEmptyOrWhitespace(courseRequest.getEndDate(), "endDate", "The end date is required", errors);

        if (courseRequest.getEndDate().isBefore(courseRequest.getStartDate())) {
            errors.rejectValue("endDate", "The end date must be after the start date");
        }
    }


}