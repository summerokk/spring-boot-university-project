package com.att.university.validator.person;

import com.att.university.H2Config;
import com.att.university.request.person.student.StudentRegisterRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = H2Config.class)
class PersonValidatorTest {
    @Autowired
    private StudentRegisterValidator studentRegisterValidator;

    @Test
    void personValidatorShouldNotThrowExceptionIfRequestIsValid() {
        StudentRegisterRequest studentRegisterRequest = StudentRegisterRequest.builder()
                .withFirstName("test")
                .withLastName("test")
                .withEmail("test@test.ru")
                .withPassword("1234567789")
                .build();

        assertDoesNotThrow(() -> studentRegisterValidator.validate(studentRegisterRequest));
    }

    @Test
    void personValidatorShouldThrowExceptionIfFirstNameIfNull() {
        StudentRegisterRequest studentRegisterRequest = StudentRegisterRequest.builder()
                .withFirstName(null)
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
                .build();

        Exception exception = assertThrows(RuntimeException.class,
                () -> studentRegisterValidator.validate(studentRegisterRequest));
        assertEquals("Email is incorrect", exception.getMessage());
    }
}