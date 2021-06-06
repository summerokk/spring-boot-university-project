package com.att.validator.course.impl;

import com.att.request.course.CourseAddRequest;
import com.att.validator.course.CourseAddValidator;
import org.springframework.stereotype.Component;

@Component
public class CourseAddValidatorImpl implements CourseAddValidator {
    @Override
    public void validate(CourseAddRequest addRequest) {
        validateBaseInfo(addRequest);
    }
}
