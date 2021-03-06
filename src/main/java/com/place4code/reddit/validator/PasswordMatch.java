package com.place4code.reddit.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordMatchValidator.class)
@Target({ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordMatch {

    String message() default "Password & Password Confirmation do not match.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}