package com.att.university.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder(setterPrefix = "with")
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
public class Teacher extends Person {
    private final String linkedin;
    private final AcademicRank academicRank;
    private final ScienceDegree scienceDegree;
}
