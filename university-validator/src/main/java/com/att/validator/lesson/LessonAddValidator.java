package com.att.validator.lesson;

import com.att.request.lesson.LessonAddRequest;

public interface LessonAddValidator extends LessonValidator<LessonAddRequest> {
    void validate(LessonAddRequest addRequest);
}
