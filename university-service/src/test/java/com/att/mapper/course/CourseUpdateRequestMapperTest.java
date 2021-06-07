package com.att.mapper.course;

import com.att.entity.Course;
import com.att.request.course.CourseUpdateRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CourseUpdateRequestMapperTest {
    private final CourseUpdateRequestMapper requestMapper = new CourseUpdateRequestMapper();

    @Test
    void convertToEntityShouldReturnEntityWhenRequestContainsAllFields() {
        CourseUpdateRequest request = new CourseUpdateRequest(1, "Course");

        Course course = requestMapper.convertToEntity(request);

        assertEquals("Course", course.getName());
    }
}
