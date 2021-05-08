package com.att.university.validator.course;

import com.att.university.request.course.CourseUpdateRequest;

public interface CourseUpdateValidator extends CourseValidator<CourseUpdateRequest> {
    void validate(CourseUpdateRequest addRequest);
}
