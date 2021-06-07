package com.att.mapper.student;

import com.att.entity.Student;
import com.att.request.person.student.StudentRegisterRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentRegisterRequestMapperTest {
    private final StudentRegisterRequestMapper requestMapper = new StudentRegisterRequestMapper();

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
}
