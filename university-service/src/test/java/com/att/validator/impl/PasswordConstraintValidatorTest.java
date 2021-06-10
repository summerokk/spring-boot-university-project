package com.att.validator.impl;

import com.att.validator.ValidPassword;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PasswordConstraintValidatorTest {
    @Mock
    private ValidPassword validPassword;

    @Mock
    private HibernateConstraintValidatorContext context;

    @Test
    void validatorShouldReturnFalseIfPasswordIsIncorrect() {
        when(context.unwrap(HibernateConstraintValidatorContext.class)).thenReturn(context);

        final ConstraintValidatorContext.ConstraintViolationBuilder builder =
                mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        when(context.buildConstraintViolationWithTemplate("{password.validation}")).thenReturn(builder);

        PasswordConstraintValidator validator = new PasswordConstraintValidator();
        validator.initialize(validPassword);

        boolean isContainsUppercaseSymbol = validator.isValid("testtest1", context);
        boolean isMinLength = validator.isValid("testt", context);
        boolean isContainsDigit = validator.isValid("Testtasdasd", context);

        assertAll(
                () -> assertFalse(isContainsUppercaseSymbol),
                () -> assertFalse(isMinLength),
                () -> assertFalse(isContainsDigit)
        );
    }

    @Test
    void validatorShouldReturnTrueIfPasswordsIsCorrect() {
        when(context.unwrap(HibernateConstraintValidatorContext.class)).thenReturn(context);

        PasswordConstraintValidator validator = new PasswordConstraintValidator();
        validator.initialize(validPassword);

        boolean result = validator.isValid("Testtest1", context);
        assertTrue(result);
    }
}
