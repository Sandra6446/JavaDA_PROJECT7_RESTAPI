package com.nnk.springboot.config;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordConstraint {
    String message() default "Invalid password : at least one capital letter, at least 8 characters, at least one number and one symbol";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}