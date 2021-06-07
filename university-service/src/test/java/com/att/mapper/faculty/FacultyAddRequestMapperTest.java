package com.att.mapper.faculty;

import com.att.entity.Faculty;
import com.att.request.faculty.FacultyAddRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FacultyAddRequestMapperTest {
    private final FacultyAddRequestMapper requestMapper = new FacultyAddRequestMapper();

    @Test
    void convertToEntityShouldReturnEntityWhenRequestContainsAllFields() {
        FacultyAddRequest request = new FacultyAddRequest("Faculty");

        Faculty faculty = requestMapper.convertToEntity(request);

        assertEquals("Faculty", faculty.getName());
    }
}
