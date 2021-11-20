package com.att.mapper.course;

import com.att.entity.Course;
import com.att.request.course.CourseAddRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseAddRequestMapper {
    Course convertToEntity(CourseAddRequest addRequest);
}
