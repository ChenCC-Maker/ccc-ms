package com.ccc.miaoshav1.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


/**
 * 这是一个实现了ConstraintValidator的类，规定要处理的注解类型为PasswordLength，要校验的字段类型为String
 */
public class PasswordLengthValidator implements ConstraintValidator<PasswordLength,String> {

    private int min;
    private int max;

    @Override
    public void initialize(PasswordLength constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }


    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        // 不需要单独处理密码为空的场景，为空的情况，使用@NotNull进行处理
        if(value == null) return true;
        return value.length() >= min && value.length() <= max;
    }

}
