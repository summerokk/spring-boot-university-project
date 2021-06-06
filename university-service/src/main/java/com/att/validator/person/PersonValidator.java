package com.att.validator.person;

import com.att.exception.service.PasswordTooShortException;
import com.att.exception.service.PasswordsAreNotTheSameException;
import com.att.validator.Validator;

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
