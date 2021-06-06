package com.att.validator.course;

import com.att.request.course.CourseRequest;
import com.att.validator.Validator;

public interface CourseValidator<T extends CourseRequest> extends Validator {
    default void validateBaseInfo(T course) {
        validateNull(course.getName(), "Course name is null");

        validateEmpty(course.getName(), "Course name is empty");
    }
}
