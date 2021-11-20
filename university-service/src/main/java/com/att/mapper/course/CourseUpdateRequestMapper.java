package com.att.mapper.course;

import com.att.entity.Course;
import com.att.request.course.CourseUpdateRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseUpdateRequestMapper {
    Course convertToEntity(CourseUpdateRequest updateRequest);
}
