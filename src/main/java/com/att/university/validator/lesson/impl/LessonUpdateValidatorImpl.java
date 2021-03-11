package com.att.university.validator.lesson.impl;

import com.att.university.request.lesson.LessonUpdateRequest;
import com.att.university.validator.lesson.LessonUpdateValidator;
import org.springframework.stereotype.Component;

@Component
public class LessonUpdateValidatorImpl implements LessonUpdateValidator {
    @Override
    public void validate(LessonUpdateRequest updateRequest) {
        validateBaseInfo(updateRequest);

        validateNull(updateRequest.getId(), "Lesson ID is null");
    }
}
