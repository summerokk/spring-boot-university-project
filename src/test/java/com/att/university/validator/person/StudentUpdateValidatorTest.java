package com.att.university.validator.person;


import com.att.university.request.person.student.StudentUpdateRequest;
import com.att.university.validator.person.impl.StudentUpdateValidatorImpl;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class StudentUpdateValidatorTest {
    private final StudentUpdateValidator studentUpdateValidator =
            new StudentUpdateValidatorImpl();

    @Test
    void studentUpdateValidatorShouldNotThrowRuntimeExceptionIfStudentUpdateRequestIsValid() {
        StudentUpdateRequest studentUpdateRequest = StudentUpdateRequest.builder()
                .withId(1)
                .withFirstName("test")
                .withLastName("test")
                .withEmail("test@test.ru")
                .withGroupId(1)
                .build();

        assertDoesNotThrow(() -> studentUpdateValidator.validate(studentUpdateRequest));
    }
}
