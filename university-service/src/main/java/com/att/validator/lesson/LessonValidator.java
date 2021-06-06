package com.att.validator.lesson;

import com.att.request.lesson.LessonRequest;
import com.att.validator.Validator;

public interface LessonValidator<T extends LessonRequest> extends Validator {
    default void validateBaseInfo(T lesson) {
        validateNull(lesson.getClassroomId(), "Classroom ID is null");

        validateNull(lesson.getCourseId(), "Course ID is null");

        validateNull(lesson.getTeacherId(), "Teacher ID is null");

        validateNull(lesson.getGroupId(), "Group ID is null");

        validateNull(lesson.getDate(), "Date is null");
    }
}
