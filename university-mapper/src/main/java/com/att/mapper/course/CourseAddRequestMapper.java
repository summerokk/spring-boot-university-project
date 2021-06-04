package com.att.mapper.course;

import com.att.request.course.CourseAddRequest;
import com.att.entity.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseAddRequestMapper {
    public Course convertToEntity(CourseAddRequest addRequest) {
        return new Course(null, addRequest.getName());
    }
}
