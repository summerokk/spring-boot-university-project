package com.att.university.validator.lesson.impl;

import com.att.university.request.lesson.LessonAddRequest;
import com.att.university.validator.lesson.LessonAddValidator;
import org.springframework.stereotype.Component;

@Component
public class LessonAddValidatorImpl implements LessonAddValidator {
    @Override
    public void validate(LessonAddRequest addRequest) {
        validateBaseInfo(addRequest);
    }
}
