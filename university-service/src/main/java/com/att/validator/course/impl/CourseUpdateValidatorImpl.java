package com.att.validator.course.impl;

import com.att.request.course.CourseUpdateRequest;
import com.att.validator.course.CourseUpdateValidator;
import org.springframework.stereotype.Component;

@Component
public class CourseUpdateValidatorImpl implements CourseUpdateValidator {
    @Override
    public void validate(CourseUpdateRequest updateRequest) {
        validateNull(updateRequest.getId(), "Course Id is null");

        validateBaseInfo(updateRequest);
    }
}
