package com.att.validator.course;

import com.att.request.course.CourseUpdateRequest;

public interface CourseUpdateValidator extends CourseValidator<CourseUpdateRequest> {
    void validate(CourseUpdateRequest addRequest);
}
