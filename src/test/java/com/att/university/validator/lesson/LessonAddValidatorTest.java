package com.att.university.validator.lesson;

import com.att.university.request.lesson.LessonAddRequest;
import com.att.university.validator.lesson.impl.LessonAddValidatorImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LessonAddValidatorTest {
    @Test
    void addValidatorShouldInvokeAllMethods() {
        final LessonAddRequest lessonAddRequest = LessonAddRequest.builder()
                .withTeacherId(1)
                .withClassroomId(1)
                .withDate("2004-10-20T10:23")
                .withCourseId(1)
                .withGroupId(1)
                .build();

        LessonAddValidator addValidator = spy(new LessonAddValidatorImpl());
        doNothing().when(addValidator).validateNull(any(Object.class), anyString());

        addValidator.validate(lessonAddRequest);

        verify(addValidator, times(5)).validateNull(any(Object.class), anyString());
    }
}
