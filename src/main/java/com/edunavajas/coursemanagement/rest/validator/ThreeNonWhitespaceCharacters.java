package com.edunavajas.coursemanagement.rest.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ThreeNonWhitespaceCharactersValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ThreeNonWhitespaceCharacters {
    String message() default "The field must have at least 3 non-whitespace characters";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}