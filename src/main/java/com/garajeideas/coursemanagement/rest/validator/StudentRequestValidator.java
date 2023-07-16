package com.garajeideas.coursemanagement.rest.validator;

import com.garajeideas.coursemanagement.openapi.web.rest.dtos.StudentRequest;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.time.LocalDate;
import java.time.Period;

@ControllerAdvice
public class StudentRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return StudentRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        StudentRequest studentRequest = (StudentRequest) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "The student name is required");
        if (studentRequest.getFirstName().length() < 3) {
            errors.rejectValue("firstName", "The student name must be at least 3 characters long");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "The student surname is required");
        if (studentRequest.getLastName().length() < 2) {
            errors.rejectValue("lastName", "The student surname must be at least 2 characters long");
        }

        if (studentRequest.getBirthDate() == null) {
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