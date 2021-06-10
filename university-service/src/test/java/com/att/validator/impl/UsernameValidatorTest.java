package com.att.validator.impl;

import com.att.validator.Username;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertTrue;

class UsernameValidatorTest {
    @Mock
    private Username username;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Test
    void validatorShouldReturnTrueIfNameIsCorrect() {
        UsernameValidator nameValidator = new UsernameValidator();
        nameValidator.initialize(username);

        boolean result = nameValidator.isValid("test", constraintValidatorContext);
        assertTrue(result);
    }
}
