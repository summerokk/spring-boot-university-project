package com.att.university.mapper.course;

import com.att.university.entity.Course;
import com.att.university.request.course.CourseUpdateRequest;
import org.springframework.stereotype.Component;

@Component
public class CourseUpdateRequestMapper {
    public Course convertToEntity(CourseUpdateRequest updateRequest) {
        return new Course(updateRequest.getId(), updateRequest.getName());
    }
}
