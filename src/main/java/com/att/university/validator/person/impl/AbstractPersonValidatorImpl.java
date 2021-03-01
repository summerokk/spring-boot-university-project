package com.att.university.validator.person.impl;

import com.att.university.request.person.PersonRequest;
import com.att.university.validator.person.PersonValidator;

import java.util.regex.Pattern;

public abstract class AbstractPersonValidatorImpl<T extends PersonRequest> implements PersonValidator {
    private static final Pattern EMAIL_REGEX_EXPRESSION = Pattern.compile("^\\S+@\\S+\\.\\S+$");
    private static final int MIN_PASSWORD_LENGTH = 6;

    protected void baseInfoValidate(T person) {
        validateNull(person.getFirstName(), "First name is null");

        validateNull(person.getLastName(), "Last name is null");

        validateNull(person.getEmail(), "Email is null");

        validateNull(person.getPassword(), "Password is null");

        if (person.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException(String.format("Password must be %d or more characters", MIN_PASSWORD_LENGTH));
        }

        if (!EMAIL_REGEX_EXPRESSION.matcher(person.getEmail()).find()) {
            throw new RuntimeException("Email is incorrect");
        }
    }
}
