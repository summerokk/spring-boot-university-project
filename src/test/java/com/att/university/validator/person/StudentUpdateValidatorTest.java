package com.att.university.validator.person;


import com.att.university.request.person.student.StudentUpdateRequest;
import com.att.university.validator.person.impl.StudentUpdateValidatorImpl;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class StudentUpdateValidatorTest {
    private final int MIN_PASSWORD_LENGTH = 6;
    private final StudentUpdateValidator studentUpdateValidator =
            new StudentUpdateValidatorImpl(MIN_PASSWORD_LENGTH);

    @Test
    void studentUpdateValidatorShouldNotThrowRuntimeExceptionIfStudentUpdateRequestIsValid() {
        StudentUpdateRequest studentUpdateRequest = StudentUpdateRequest.builder()
                .withId(1)
                .withFirstName("test")
                .withLastName("test")
                .withEmail("test@test.ru")
                .withPassword("te1dsf12sdfg")
                .withPasswordConfirm("te1dsf12sdfg")
                .withGroupId(1)
                .build();

        assertDoesNotThrow(() -> studentUpdateValidator.validate(studentUpdateRequest));
    }
}
