package com.att.mapper.teacher;

import com.att.entity.AcademicRank;
import com.att.entity.ScienceDegree;
import com.att.entity.Teacher;
import com.att.request.person.teacher.TeacherUpdateRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TeacherUpdateRequestMapperTest {
    private final TeacherUpdateRequestMapper requestMapper = new TeacherUpdateRequestMapper();

    @Test
    void convertToEntityShouldReturnEntityWhenRequestContainsAllFields() {
        TeacherUpdateRequest addRequest = TeacherUpdateRequest.builder()
                .withId(1)
                .withFirstName("Fedor")
                .withLastName("Fedorov")
                .withEmail("Fedorov@wewer.com")
                .withLinkedin("https://test.ru")
                .build();

        AcademicRank academicRank = new AcademicRank(1, "test");
        ScienceDegree scienceDegree = new ScienceDegree(1, "test");

        Teacher teacher = requestMapper.convertToEntity(addRequest, academicRank, scienceDegree);

        assertAll(
                () -> assertEquals(1, teacher.getId()),
                () -> assertEquals("Fedorov", teacher.getLastName()),
                () -> assertEquals("Fedorov@wewer.com", teacher.getEmail()),
                () -> assertEquals(academicRank, teacher.getAcademicRank()),
                () -> assertEquals(scienceDegree, teacher.getScienceDegree()),
                () -> assertEquals("Fedor", teacher.getFirstName()),
                () -> assertEquals("https://test.ru", teacher.getLinkedin())
        );
    }
}
