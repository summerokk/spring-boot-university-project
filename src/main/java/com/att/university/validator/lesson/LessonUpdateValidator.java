package com.att.university.validator.lesson;

import com.att.university.request.lesson.LessonUpdateRequest;

public interface LessonUpdateValidator extends LessonValidator<LessonUpdateRequest> {
    void validate(LessonUpdateRequest updateRequest);
}
