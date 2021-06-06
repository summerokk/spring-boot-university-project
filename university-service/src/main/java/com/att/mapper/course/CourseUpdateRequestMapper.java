package com.att.mapper.course;

import com.att.request.course.CourseUpdateRequest;
import com.att.entity.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseUpdateRequestMapper {
    public Course convertToEntity(CourseUpdateRequest updateRequest) {
        return new Course(updateRequest.getId(), updateRequest.getName());
    }
}
