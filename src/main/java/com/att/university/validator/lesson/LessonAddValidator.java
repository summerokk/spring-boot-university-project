package com.att.university.validator.lesson;

import com.att.university.request.lesson.LessonAddRequest;

public interface LessonAddValidator extends LessonValidator<LessonAddRequest> {
    void validate(LessonAddRequest addRequest);
}
