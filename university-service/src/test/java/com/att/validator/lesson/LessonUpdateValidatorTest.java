package com.att.validator.lesson;

import com.att.request.lesson.LessonUpdateRequest;
import com.att.validator.lesson.impl.LessonUpdateValidatorImpl;
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
class LessonUpdateValidatorTest {
    @Test
    void addValidatorShouldInvokeAllMethods() {
        final LessonUpdateRequest updateRequest = LessonUpdateRequest.builder()
                .withId(1)
                .withTeacherId(1)
                .withClassroomId(1)
                .withDate(LocalDateTime.parse("2004-10-20T10:23"))
                .withCourseId(1)
                .withGroupId(1)
                .build();

        LessonUpdateValidator updateValidator = Mockito.spy(new LessonUpdateValidatorImpl());
        doNothing().when(updateValidator).validateNull(any(Object.class), anyString());

        updateValidator.validate(updateRequest);

        verify(updateValidator, times(6)).validateNull(any(Object.class), anyString());
    }
}
