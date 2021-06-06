package com.att.validator.lesson;

import com.att.request.lesson.LessonAddRequest;
import com.att.validator.lesson.impl.LessonAddValidatorImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

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
                .withDate(LocalDateTime.parse("2004-10-20T10:23"))
                .withCourseId(1)
                .withGroupId(1)
                .build();

        LessonAddValidator addValidator = Mockito.spy(new LessonAddValidatorImpl());
        doNothing().when(addValidator).validateNull(any(Object.class), anyString());

        addValidator.validate(lessonAddRequest);

        verify(addValidator, times(5)).validateNull(any(Object.class), anyString());
    }
}
