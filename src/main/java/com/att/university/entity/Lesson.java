package com.att.university.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Builder(setterPrefix = "with", toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
public class Lesson {
    private final Integer id;
    private final Course course;
    private final Group group;
    private final Teacher teacher;
    private final LocalDateTime date;
    private final Classroom classroom;
}
