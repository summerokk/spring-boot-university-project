package com.att.university.service;

import com.att.university.request.lesson.LessonAddRequest;
import com.att.university.request.lesson.LessonUpdateRequest;

public interface LessonService {
    void add(LessonAddRequest addRequest);

    void update(LessonUpdateRequest updateRequest);
}
