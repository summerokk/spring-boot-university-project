package com.att.validator.course;

import com.att.request.course.CourseAddRequest;

public interface CourseAddValidator extends CourseValidator<CourseAddRequest> {
    void validate(CourseAddRequest addRequest);
}
