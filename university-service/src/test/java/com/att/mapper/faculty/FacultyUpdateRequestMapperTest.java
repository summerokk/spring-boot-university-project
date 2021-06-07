package com.att.mapper.faculty;

import com.att.entity.Faculty;
import com.att.request.faculty.FacultyUpdateRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FacultyUpdateRequestMapperTest {
    private final FacultyUpdateRequestMapper requestMapper = new FacultyUpdateRequestMapper();

    @Test
    void convertToEntityShouldReturnEntityWhenRequestContainsAllFields() {
        FacultyUpdateRequest request = new FacultyUpdateRequest(1, "Faculty");

        Faculty faculty = requestMapper.convertToEntity(request);

        assertEquals(1, faculty.getId());
        assertEquals("Faculty", faculty.getName());
    }
}
