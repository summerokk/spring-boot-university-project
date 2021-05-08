package com.att.university.mapper.course;

import com.att.university.entity.Course;
import com.att.university.request.course.CourseAddRequest;
import org.springframework.stereotype.Component;

@Component
public class CourseAddRequestMapper {
    public Course convertToEntity(CourseAddRequest addRequest) {
        return new Course(null, addRequest.getName());
    }
}
