package com.att.validator.lesson;

import com.att.request.lesson.LessonUpdateRequest;

public interface LessonUpdateValidator extends LessonValidator<LessonUpdateRequest> {
    void validate(LessonUpdateRequest updateRequest);
}
