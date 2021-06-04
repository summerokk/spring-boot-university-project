package com.att.validator.lesson.impl;

import com.att.request.lesson.LessonUpdateRequest;
import com.att.validator.lesson.LessonUpdateValidator;
import org.springframework.stereotype.Component;

@Component
public class LessonUpdateValidatorImpl implements LessonUpdateValidator {
    @Override
    public void validate(LessonUpdateRequest updateRequest) {
        validateBaseInfo(updateRequest);

        validateNull(updateRequest.getId(), "Lesson ID is null");
    }
}
