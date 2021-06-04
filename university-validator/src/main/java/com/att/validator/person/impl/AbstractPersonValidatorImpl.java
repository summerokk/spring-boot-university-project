package com.att.validator.person.impl;

import com.att.exception.service.NameIncorrectException;
import com.att.exception.service.WrongEmailFormatException;
import com.att.request.person.PersonRequest;
import com.att.validator.person.PersonValidator;

import java.util.regex.Pattern;

public abstract class AbstractPersonValidatorImpl<T extends PersonRequest> implements PersonValidator {
    private static final Pattern EMAIL_REGEX_EXPRESSION = Pattern.compile("^\\S+@\\S+\\.\\S+$");
    private static final Pattern NAME_REGEX_EXPRESSION = Pattern.compile("^[a-zA-Z]{2,}+$");

    protected void baseInfoValidate(T person) {
        validateNull(person.getFirstName(), "First name is null");

        validateNull(person.getLastName(), "Last name is null");

        validateNull(person.getEmail(), "Email is null");

        if (!NAME_REGEX_EXPRESSION.matcher(person.getFirstName()).find()) {
            throw new NameIncorrectException("First name is incorrect");
        }

        if (!NAME_REGEX_EXPRESSION.matcher(person.getLastName()).find()) {
            throw new NameIncorrectException("Last name is incorrect");
        }

        if (!EMAIL_REGEX_EXPRESSION.matcher(person.getEmail()).find()) {
            throw new WrongEmailFormatException("Email is incorrect");
        }
    }
}
