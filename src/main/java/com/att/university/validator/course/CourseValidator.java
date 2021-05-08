package com.att.university.validator.course;

import com.att.university.request.course.CourseRequest;
import com.att.university.validator.Validator;

public interface CourseValidator<T extends CourseRequest> extends Validator {
    default void validateBaseInfo(T course) {
        validateNull(course.getName(), "Course name is null");

        validateEmpty(course.getName(), "Course name is empty");
    }
}
