package com.att.university.request.lesson;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LessonAddRequest extends LessonRequest {
    @Builder(setterPrefix = "with")
    public LessonAddRequest(Integer courseId, Integer groupId, Integer classroomId, Integer teacherId,
                                  LocalDateTime date) {
        super(courseId, groupId, classroomId, teacherId, date);
    }
}
