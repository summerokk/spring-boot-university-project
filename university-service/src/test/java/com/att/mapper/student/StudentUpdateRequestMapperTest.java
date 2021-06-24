package com.att.mapper.student;

import com.att.entity.Faculty;
import com.att.entity.Group;
import com.att.entity.Student;
import com.att.request.person.student.StudentUpdateRequest;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class StudentUpdateRequestMapperTest {
    private final StudentUpdateRequestMapper requestMapper = Mappers.getMapper(StudentUpdateRequestMapper.class);

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
                () -> assertEquals(1, student.getId()),
                () -> assertEquals("Fedor", student.getFirstName()),
                () -> assertEquals(group, student.getGroup())
        );
    }

    @Test
    void convertToEntityShouldReturnNullWhenConvertParametersAreNull() {
        Student student = requestMapper.convertToEntity(null, null, null);

        assertNull(student);
    }

    @Test
    void convertToEntityShouldReturnNullWhenRequestIsNull() {
        Group group = new Group(1, "test", new Faculty(1, "faculty"));

        String password = "12345678";

        Student student = requestMapper.convertToEntity(null, group, password);

        assertNull(student.getId());
        assertNull(student.getEmail());
        assertNull(student.getFirstName());
        assertNull(student.getLastName());
        assertEquals(group, student.getGroup());
        assertEquals(password, student.getPassword());
    }

    @Test
    void convertToEntityShouldReturnNullWhenConvertParametersAreNullExpectRequest() {
        StudentUpdateRequest request = StudentUpdateRequest.builder()
                .withId(1)
                .withFirstName("Fedor")
                .withLastName("Fedorov")
                .withEmail("test@rwer.com")
                .build();

        Student student = requestMapper.convertToEntity(request, null, null);

        assertAll(
                () -> assertEquals("Fedorov", student.getLastName()),
                () -> assertEquals("Fedor", student.getFirstName()),
                () -> assertEquals("test@rwer.com", student.getEmail()),
                () -> assertNull(student.getGroup())
        );
    }

    @Test
    void convertToEntityShouldReturnNullWhenConvertParametersAreNullExpectPassword() {
        String password = "Pwasfa113";

        Student student = requestMapper.convertToEntity(null, null, password);

        assertAll(
                () -> assertNull(student.getFirstName()),
                () -> assertNull(student.getGroup())
        );
    }
}
