package com.att.university.validator.course;

import com.att.university.request.course.CourseAddRequest;

public interface CourseAddValidator extends CourseValidator<CourseAddRequest> {
    void validate(CourseAddRequest addRequest);
}
