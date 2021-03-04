package com.att.university.validator.lesson;

import com.att.university.H2Config;
import com.att.university.request.lesson.LessonAddRequest;
import com.att.university.request.lesson.LessonUpdateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = H2Config.class)
class LessonUpdateValidatorTest {
    @Autowired
    private LessonUpdateValidator lessonUpdateValidator;

    @Test
    void lessonUpdateValidatorShouldNotThrowExceptionIfLessonUpdateRequestIsValid() {
        LessonUpdateRequest lessonUpdateRequest = LessonUpdateRequest.builder()
                .withId(1)
                .withTeacherId(1)
                .withClassroomId(1)
                .withDate("2004-10-20T10:23")
                .withCourseId(1)
                .withGroupId(1)
                .build();

        assertDoesNotThrow(() -> lessonUpdateValidator.validate(lessonUpdateRequest));
    }
}
