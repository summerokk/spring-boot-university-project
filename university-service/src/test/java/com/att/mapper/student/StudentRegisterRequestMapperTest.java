package com.att.mapper.student;

import com.att.entity.Student;
import com.att.request.person.student.StudentRegisterRequest;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class StudentRegisterRequestMapperTest {
    private final StudentRegisterRequestMapper requestMapper = Mappers.getMapper(StudentRegisterRequestMapper.class);

    @Test
    void convertToEntityShouldReturnEntityWhenRequestContainsAllFields() {
        StudentRegisterRequest request = StudentRegisterRequest.builder()
                .withFirstName("Fedor")
                .withEmail("test@rwer.com")
                .withLastName("Fedorov")
                .build();

        String password = "12345678";

        Student student = requestMapper.convertToEntity(request, password);

        assertAll(
                () -> assertEquals("Fedorov", student.getLastName()),
                () -> assertEquals("Fedor", student.getFirstName()),
                () -> assertEquals("test@rwer.com", student.getEmail())
        );
    }

    @Test
    void convertToEntityShouldReturnNullWhenConvertParametersAreNull() {
        Student student = requestMapper.convertToEntity(null, null);

        assertNull(student);
    }

    @Test
    void convertToEntityShouldReturnNullWhenRequestIsNull() {
        String password = "12345678";

        Student student = requestMapper.convertToEntity(null, password);

        assertNull(student.getId());
        assertNull(student.getEmail());
        assertNull(student.getFirstName());
        assertNull(student.getLastName());
        assertEquals(password, student.getPassword());
    }

    @Test
    void convertToEntityShouldReturnNullWhenConvertParametersAreNullExpectRequest() {
        StudentRegisterRequest request = StudentRegisterRequest.builder()
                .withFirstName("Fedor")
                .withEmail("test@rwer.com")
                .withLastName("Fedorov")
                .build();

        Student student = requestMapper.convertToEntity(request, null);

        assertAll(
                () -> assertEquals("Fedorov", student.getLastName()),
                () -> assertEquals("Fedor", student.getFirstName()),
                () -> assertEquals("test@rwer.com", student.getEmail()),
                () -> assertNull(student.getPassword())
        );
    }
}
