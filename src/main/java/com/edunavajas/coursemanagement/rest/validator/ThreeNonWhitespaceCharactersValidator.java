package com.edunavajas.coursemanagement.rest.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ThreeNonWhitespaceCharactersValidator implements ConstraintValidator<ThreeNonWhitespaceCharacters, String> {
    @Override
    public void initialize(ThreeNonWhitespaceCharacters constraintAnnotation) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.matches(".*\\S.*\\S.*\\S.*");
    }
}