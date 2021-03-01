package com.att.university.validator.person;

import com.att.university.H2Config;
import com.att.university.request.person.teacher.TeacherRegisterRequest;
import com.att.university.request.person.teacher.TeacherUpdateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = H2Config.class)
class TeacherRegisterValidatorTest {
    @Autowired
    private TeacherRegisterValidator teacherRegisterValidator;

    @Test
    void teacherRegisterValidatorShouldNotThrowRuntimeExceptionIfTeacherRegisterRequestIsValid() {
        TeacherRegisterRequest teacherRegisterRequest = TeacherRegisterRequest.builder()
                .withFirstName("test")
                .withLastName("test")
                .withEmail("test@test.ru")
                .withPassword("te1dsf12sdfg")
                .withLinkedin("http://test.ru")
                .withAcademicRankId(1)
                .withScienceDegreeId(1)
                .build();

        assertDoesNotThrow(() -> teacherRegisterValidator.validate(teacherRegisterRequest));
    }
}