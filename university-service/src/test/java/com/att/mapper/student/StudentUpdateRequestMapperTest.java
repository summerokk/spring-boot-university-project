package com.att.mapper.student;

import com.att.entity.Faculty;
import com.att.entity.Group;
import com.att.entity.Student;
import com.att.request.person.student.StudentUpdateRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentUpdateRequestMapperTest {
    private final StudentUpdateRequestMapper requestMapper = new StudentUpdateRequestMapper();

    @Test
    void convertToEntityShouldReturnEntityWhenRequestContainsAllFields() {
        StudentUpdateRequest request = StudentUpdateRequest.builder()
                .withId(1)
                .withFirstName("Fedor")
                .withLastName("Fedorov")
                .build();

        Group group = new Group(1, "test", new Faculty(1, "faculty"));

        String password = "12345678";

        Student student = requestMapper.convertToEntity(request, group, password);

        assertAll(
                () -> assertEquals("Fedorov", student.getLastName()),
                () -> assertEquals("Fedor", student.getFirstName()),
                () -> assertEquals(group, student.getGroup())
        );
    }
}
