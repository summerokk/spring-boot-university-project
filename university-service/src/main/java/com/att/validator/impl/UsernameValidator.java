package com.att.validator.impl;

import com.att.validator.Username;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class UsernameValidator implements ConstraintValidator<Username, String> {
    private static final Pattern NAME_REGEX_EXPRESSION = Pattern.compile("^[a-zA-Z]{2,}+$");

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return NAME_REGEX_EXPRESSION.matcher(name).find();
    }
}
