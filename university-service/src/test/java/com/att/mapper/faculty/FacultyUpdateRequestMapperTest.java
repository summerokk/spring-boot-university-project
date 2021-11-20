package com.att.mapper.faculty;

import com.att.entity.Faculty;
import com.att.request.faculty.FacultyUpdateRequest;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class FacultyUpdateRequestMapperTest {
    private final FacultyUpdateRequestMapper requestMapper = Mappers.getMapper(FacultyUpdateRequestMapper.class);

    @Test
    void convertToEntityShouldReturnEntityWhenRequestContainsAllFields() {
        FacultyUpdateRequest request = new FacultyUpdateRequest(1, "Faculty");

        Faculty faculty = requestMapper.convertToEntity(request);

        assertEquals(1, faculty.getId());
        assertEquals("Faculty", faculty.getName());
    }

    @Test
    void convertToEntityShouldReturnNullWhenRequestIsNull() {
        Faculty faculty = requestMapper.convertToEntity(null);

        assertNull(faculty);
    }
}
