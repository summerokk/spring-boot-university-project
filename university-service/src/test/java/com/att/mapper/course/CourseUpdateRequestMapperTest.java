package com.att.mapper.course;

import com.att.entity.Course;
import com.att.request.course.CourseUpdateRequest;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CourseUpdateRequestMapperTest {
    private final CourseUpdateRequestMapper requestMapper = Mappers.getMapper(CourseUpdateRequestMapper.class);

    @Test
    void convertToEntityShouldReturnEntityWhenRequestContainsAllFields() {
        CourseUpdateRequest request = new CourseUpdateRequest(1, "Course");

        Course course = requestMapper.convertToEntity(request);

        assertEquals(1, course.getId());
        assertEquals("Course", course.getName());
    }

    @Test
    void convertToEntityShouldReturnNullWhenRequestIsNull() {
        Course course = requestMapper.convertToEntity(null);

        assertNull(course);
    }
}
