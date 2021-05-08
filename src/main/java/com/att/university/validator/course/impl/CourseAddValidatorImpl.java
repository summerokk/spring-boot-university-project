package com.att.university.validator.course.impl;

import com.att.university.request.course.CourseAddRequest;
import com.att.university.validator.course.CourseAddValidator;
import org.springframework.stereotype.Component;

@Component
public class CourseAddValidatorImpl implements CourseAddValidator {
    @Override
    public void validate(CourseAddRequest addRequest) {
        validateBaseInfo(addRequest);
    }
}
