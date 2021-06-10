package com.att.validator.impl;

import com.att.validator.ValidPassword;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {
    private static final String ERROR_MESSAGE = "{password.validation}";
    private static final Pattern DIGIT_REGEX_EXPRESSION = Pattern.compile("[\\d]+");
    private static final Pattern UPPERCASE_REGEX_EXPRESSION = Pattern.compile("[A-Z]+");
    private static final int MIN_PASSWORD_LENGTH = 7;
    private static final int MIN_COUNT_UPPERCASE_SYMBOLS = 1;
    private static final int MIN_COUNT_DIGITS = 1;

    @Override
    public boolean isValid(String password, ConstraintValidatorContext validatorContext) {
        HibernateConstraintValidatorContext hibernateValidatorContext =
                validatorContext.unwrap(HibernateConstraintValidatorContext.class);

        hibernateValidatorContext.disableDefaultConstraintViolation();

        if (this.isPasswordMinLength(password)
                && this.isPasswordContainsDigits(password)
                && this.isPasswordContainsUppercaseSymbols(password)) {
            return true;
        }

        hibernateValidatorContext.addMessageParameter("length", MIN_PASSWORD_LENGTH);
        hibernateValidatorContext.addMessageParameter("digits", MIN_COUNT_DIGITS);
        hibernateValidatorContext.addMessageParameter("uppercase", MIN_COUNT_UPPERCASE_SYMBOLS);
        hibernateValidatorContext.buildConstraintViolationWithTemplate(ERROR_MESSAGE)
                .addConstraintViolation();

        return false;
    }

    private boolean isPasswordMinLength(String password) {
        return password.length() >= MIN_PASSWORD_LENGTH;
    }

    private boolean isPasswordContainsDigits(String password) {
        return DIGIT_REGEX_EXPRESSION.matcher(password).find();
    }

    private boolean isPasswordContainsUppercaseSymbols(String password) {
        return UPPERCASE_REGEX_EXPRESSION.matcher(password).find();
    }
}
