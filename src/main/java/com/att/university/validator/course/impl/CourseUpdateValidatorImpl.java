package com.att.university.validator.course.impl;

import com.att.university.request.course.CourseUpdateRequest;
import com.att.university.validator.course.CourseUpdateValidator;
import org.springframework.stereotype.Component;

@Component
public class CourseUpdateValidatorImpl implements CourseUpdateValidator {
    @Override
    public void validate(CourseUpdateRequest updateRequest) {
        validateNull(updateRequest.getId(), "Course Id is null");

        validateBaseInfo(updateRequest);
    }
}
