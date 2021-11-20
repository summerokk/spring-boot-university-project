package com.att.mapper.course;

import com.att.entity.Course;
import com.att.request.course.CourseAddRequest;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CourseAddRequestMapperTest {
    private final CourseAddRequestMapper requestMapper = Mappers.getMapper(CourseAddRequestMapper.class);

    @Test
    void convertToEntityShouldReturnEntityWhenRequestContainsAllFields() {
        CourseAddRequest request = new CourseAddRequest("Course");

        Course course = requestMapper.convertToEntity(request);

        assertEquals("Course", course.getName());
    }

    @Test
    void convertToEntityShouldReturnNullWhenRequestIsNull() {
        Course course = requestMapper.convertToEntity(null);

        assertNull(course);
    }
}
