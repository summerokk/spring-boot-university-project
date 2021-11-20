package com.att.mapper.faculty;

import com.att.entity.Faculty;
import com.att.request.faculty.FacultyAddRequest;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class FacultyAddRequestMapperTest {
    private final FacultyAddRequestMapper requestMapper = Mappers.getMapper(FacultyAddRequestMapper.class);

    @Test
    void convertToEntityShouldReturnEntityWhenRequestContainsAllFields() {
        FacultyAddRequest request = new FacultyAddRequest("Faculty");

        Faculty faculty = requestMapper.convertToEntity(request);

        assertEquals("Faculty", faculty.getName());
    }

    @Test
    void convertToEntityShouldReturnNullWhenRequestIsNull() {
        Faculty faculty = requestMapper.convertToEntity(null);

        assertNull(faculty);
    }
}
