package com.ccc.miaoshav1.validator;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PasswordLengthValidator.class)
public @interface PasswordLength {

     int min() default 8;
     int max() default 18;
     String message() default "密码长度必须在8-18位之间";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
