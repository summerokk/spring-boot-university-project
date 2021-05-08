package com.att.university.validator.person;

import com.att.university.exception.service.PasswordTooShortException;
import com.att.university.exception.service.PasswordsAreNotTheSameException;
import com.att.university.validator.Validator;

public interface PersonValidator extends Validator {
    default void validatePasswordLength(String password, int minLength) {
        if (password.length() < minLength) {
            throw new PasswordTooShortException(String.format("Password must be %d or more characters", minLength));
        }
    }

    default void validatePasswordEquality(String password, String passwordConfirm) {
        if (!password.equals(passwordConfirm)) {
            throw new PasswordsAreNotTheSameException("Passwords must be equal");
        }
    }
}
