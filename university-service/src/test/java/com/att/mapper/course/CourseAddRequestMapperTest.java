package com.att.mapper.course;

import com.att.entity.Course;
import com.att.request.course.CourseAddRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CourseAddRequestMapperTest {
    private final CourseAddRequestMapper requestMapper = new CourseAddRequestMapper();

    @Test
    void convertToEntityShouldReturnEntityWhenRequestContainsAllFields() {
        CourseAddRequest request = new CourseAddRequest("Course");

        Course course = requestMapper.convertToEntity(request);

        assertEquals("Course", course.getName());
    }
}
