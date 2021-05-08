package com.att.university.validator.person;

import com.att.university.exception.service.NameIncorrectException;
import com.att.university.exception.service.PasswordsAreNotTheSameException;
import com.att.university.exception.service.WrongEmailFormatException;
import com.att.university.request.person.student.StudentRegisterRequest;
import com.att.university.validator.person.impl.StudentRegisterValidatorImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonValidatorTest {
    private final int MIN_PASSWORD_LENGTH = 6;
    private final StudentRegisterValidator studentRegisterValidator =
            new StudentRegisterValidatorImpl(MIN_PASSWORD_LENGTH);

    @Test
    void personValidatorShouldNotThrowExceptionIfRequestIsValid() {
        StudentRegisterRequest studentRegisterRequest = StudentRegisterRequest.builder()
                .withFirstName("test")
                .withLastName("test")
                .withEmail("test@test.ru")
                .withPassword("1234567789")
                .withPasswordConfirm("1234567789")
                .build();

        assertDoesNotThrow(() -> studentRegisterValidator.validate(studentRegisterRequest));
    }

    @Test
    void personValidatorShouldThrowExceptionIfFirstNameIfNull() {
        StudentRegisterRequest studentRegisterRequest = StudentRegisterRequest.builder()
                .withFirstName(null)
                .withPassword("testasdassd")
                .withPasswordConfirm("testasdassd")
                .build();

        Exception exception = assertThrows(RuntimeException.class,
                () -> studentRegisterValidator.validate(studentRegisterRequest));
        assertEquals("First name is null", exception.getMessage());
    }

    @Test
    void personValidatorShouldThrowExceptionIfPasswordIsLessThanMustBe() {
        StudentRegisterRequest studentRegisterRequest = StudentRegisterRequest.builder()
                .withFirstName("test")
                .withLastName("test")
                .withEmail("test@test.ru")
                .withPassword("12345")
                .build();

        Exception exception = assertThrows(RuntimeException.class,
                () -> studentRegisterValidator.validate(studentRegisterRequest));
        assertEquals(String.format("Password must be %d or more characters", 6), exception.getMessage());
    }

    @Test
    void personValidatorShouldThrowExceptionIfEmailIsNotCorrect() {
        StudentRegisterRequest studentRegisterRequest = StudentRegisterRequest.builder()
                .withFirstName("test")
                .withLastName("test")
                .withEmail("test")
                .withPassword("123451233")
                .withPasswordConfirm("123451233")
                .build();

        Exception exception = assertThrows(WrongEmailFormatException.class,
                () -> studentRegisterValidator.validate(studentRegisterRequest));
        assertEquals("Email is incorrect", exception.getMessage());
    }

    @Test
    void personValidatorShouldThrowExceptionIfFirstnameIsNotCorrect() {
        StudentRegisterRequest studentRegisterRequest = StudentRegisterRequest.builder()
                .withFirstName("1test")
                .withLastName("test")
                .withEmail("test")
                .withPassword("123451233")
                .withPasswordConfirm("123451233")
                .build();

        Exception exception = assertThrows(NameIncorrectException.class,
                () -> studentRegisterValidator.validate(studentRegisterRequest));
        assertEquals("First name is incorrect", exception.getMessage());
    }

    @Test
    void personValidatorShouldThrowExceptionIfLastnameIsNotCorrect() {
        StudentRegisterRequest studentRegisterRequest = StudentRegisterRequest.builder()
                .withFirstName("test")
                .withLastName("1test")
                .withEmail("test")
                .withPassword("123451233")
                .withPasswordConfirm("123451233")
                .build();

        Exception exception = assertThrows(NameIncorrectException.class,
                () -> studentRegisterValidator.validate(studentRegisterRequest));
        assertEquals("Last name is incorrect", exception.getMessage());
    }

    @Test
    void personValidatorShouldThrowExceptionIfPasswordAreNotTheSame() {
        StudentRegisterRequest studentRegisterRequest = StudentRegisterRequest.builder()
                .withFirstName("test")
                .withLastName("test")
                .withEmail("test")
                .withPassword("1234512331")
                .withPasswordConfirm("123451233")
                .build();

        Exception exception = assertThrows(PasswordsAreNotTheSameException.class,
                () -> studentRegisterValidator.validate(studentRegisterRequest));
        assertEquals("Passwords must be equal", exception.getMessage());
    }
}
