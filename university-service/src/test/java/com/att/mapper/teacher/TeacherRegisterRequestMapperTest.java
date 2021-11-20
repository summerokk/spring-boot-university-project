package com.att.mapper.teacher;

import com.att.entity.AcademicRank;
import com.att.entity.ScienceDegree;
import com.att.entity.Teacher;
import com.att.request.person.teacher.TeacherRegisterRequest;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TeacherRegisterRequestMapperTest {
    private final TeacherRegisterRequestMapper requestMapper = Mappers.getMapper(TeacherRegisterRequestMapper.class);

    @Test
    void convertToEntityShouldReturnEntityWhenRequestContainsAllFields() {
        TeacherRegisterRequest addRequest = TeacherRegisterRequest.builder()
                .withFirstName("Fedor")
                .withLastName("Fedorov")
                .withLinkedin("https://test.ru")
                .build();

        AcademicRank academicRank = new AcademicRank(1, "test");
        ScienceDegree scienceDegree = new ScienceDegree(1, "test");

        String password = "1235678";

        Teacher teacher = requestMapper.convertToEntity(addRequest, password, academicRank, scienceDegree);

        assertAll(
                () -> assertEquals("Fedorov", teacher.getLastName()),
                () -> assertEquals(academicRank, teacher.getAcademicRank()),
                () -> assertEquals(scienceDegree, teacher.getScienceDegree()),
                () -> assertEquals(password, teacher.getPassword()),
                () -> assertEquals("Fedor", teacher.getFirstName()),
                () -> assertEquals("https://test.ru", teacher.getLinkedin())
        );
    }
}
