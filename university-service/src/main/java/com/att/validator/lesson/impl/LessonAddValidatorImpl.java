package com.att.validator.lesson.impl;

import com.att.request.lesson.LessonAddRequest;
import com.att.validator.lesson.LessonAddValidator;
import org.springframework.stereotype.Component;

@Component
public class LessonAddValidatorImpl implements LessonAddValidator {
    @Override
    public void validate(LessonAddRequest addRequest) {
        validateBaseInfo(addRequest);
    }
}
