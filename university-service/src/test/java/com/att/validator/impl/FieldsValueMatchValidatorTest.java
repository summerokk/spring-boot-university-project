package com.att.validator.impl;

import com.att.request.person.student.StudentRegisterRequest;
import com.att.validator.FieldsValueMatch;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FieldsValueMatchValidatorTest {
    @Mock
    private FieldsValueMatch fieldsValueMatch;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Test
    void validatorShouldReturnTrueIfPasswordsAreEqual() {
        when(fieldsValueMatch.field()).thenReturn("password");
        when(fieldsValueMatch.fieldMatch()).thenReturn("passwordConfirm");

        FieldsValueMatchValidator validator = new FieldsValueMatchValidator();
        validator.initialize(fieldsValueMatch);

        StudentRegisterRequest request = StudentRegisterRequest.builder()
                .withEmail("test@test.ru")
                .withFirstName("test")
                .withLastName("testov")
                .withPassword("Testtest1")
                .withPasswordConfirm("Testtest1")
                .build();

        boolean result = validator.isValid(request, constraintValidatorContext);
        assertTrue(result);
    }
}
