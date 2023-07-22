package com.edunavajas.coursemanagement.rest.validator;

import com.edunavajas.coursemanagement.openapi.web.rest.dtos.StudentRequest;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

@ControllerAdvice
public class StudentRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return StudentRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        StudentRequest studentRequest = (StudentRequest) target;
        validateFirstName(studentRequest, errors);
        validateLastName(studentRequest, errors);
        validateBirthDate(studentRequest, errors);
    }

    private void validateFirstName(StudentRequest studentRequest, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "The student name is required");
        if (!studentRequest.getFirstName().matches(".*\\S.*\\S.*\\S.*")) {
            errors.rejectValue("firstName", "The student name must be at least 3 non-whitespace characters");
        }
    }

    private void validateLastName(StudentRequest studentRequest, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "The student surname is required");
        if (!studentRequest.getLastName().matches(".*\\S.*\\S.*")) {
            errors.rejectValue("lastName", "The student surname must be at least 2 non-whitespace characters");
        }
    }

    private void validateBirthDate(StudentRequest studentRequest, Errors errors) {
        if (Objects.isNull(studentRequest.getBirthDate())) {
            errors.rejectValue("birthDate", "The birth date is required");
        } else {
            LocalDate today = LocalDate.now();
            LocalDate birthDate = studentRequest.getBirthDate();
            int age = Period.between(birthDate, today).getYears();
            if (age < 18) {
                errors.rejectValue("birthDate", "The student must be at least 18 years old");
            }
        }
    }

}