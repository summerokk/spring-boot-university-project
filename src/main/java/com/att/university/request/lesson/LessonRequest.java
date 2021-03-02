package com.att.university.request.lesson;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder(setterPrefix = "with", toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
public abstract class LessonRequest {
    private final Integer courseId;
    private final Integer groupId;
    private final Integer classroomId;
    private final Integer teacherId;
    private final String date;
}
