package com.att.university.validator.person;

import com.att.university.request.person.teacher.TeacherUpdateRequest;
import com.att.university.validator.person.impl.TeacherUpdateValidatorImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


class TeacherUpdateValidatorTest {
    private final int MIN_PASSWORD_LENGTH = 6;
    private final TeacherUpdateValidator teacherUpdateValidator =
            new TeacherUpdateValidatorImpl(MIN_PASSWORD_LENGTH);

    @Test
    void teacherUpdateValidatorShouldNotThrowRuntimeExceptionIfTeacherUpdateRequestIsValid() {
        TeacherUpdateRequest teacherUpdateRequest = TeacherUpdateRequest.builder()
                .withId(1)
                .withFirstName("test")
                .withLastName("test")
                .withEmail("test@test.ru")
                .withPassword("te1dsf12sdfg")
                .withPasswordConfirm("te1dsf12sdfg")
                .withLinkedin("http://test.ru")
                .withAcademicRankId(1)
                .withScienceDegreeId(1)
                .build();

        assertDoesNotThrow(() -> teacherUpdateValidator.validate(teacherUpdateRequest));
    }
}
