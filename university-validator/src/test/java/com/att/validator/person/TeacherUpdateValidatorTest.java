package com.att.validator.person;

import com.att.request.person.teacher.TeacherUpdateRequest;
import com.att.validator.person.impl.TeacherUpdateValidatorImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


class TeacherUpdateValidatorTest {
    private final TeacherUpdateValidator teacherUpdateValidator = new TeacherUpdateValidatorImpl();

    @Test
    void teacherUpdateValidatorShouldNotThrowRuntimeExceptionIfTeacherUpdateRequestIsValid() {
        TeacherUpdateRequest teacherUpdateRequest = TeacherUpdateRequest.builder()
                .withId(1)
                .withFirstName("test")
                .withLastName("test")
                .withEmail("test@test.ru")
                .withLinkedin("http://test.ru")
                .withAcademicRankId(1)
                .withScienceDegreeId(1)
                .build();

        assertDoesNotThrow(() -> teacherUpdateValidator.validate(teacherUpdateRequest));
    }
}
