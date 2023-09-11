package com.olegstashkiv.booksstore.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FieldMatchValidator.class)
public @interface FieldMatch {
    String message() default "Passwords don't match";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String first();
    String second();
}
