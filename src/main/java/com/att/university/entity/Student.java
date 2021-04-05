package com.att.university.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder(setterPrefix = "with", toBuilder = true)
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Student extends Person {
    private final Group group;
}
