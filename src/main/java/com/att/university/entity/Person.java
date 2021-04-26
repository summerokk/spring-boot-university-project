package com.att.university.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder(setterPrefix = "with", toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
public abstract class Person {
    private final Integer id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
}
