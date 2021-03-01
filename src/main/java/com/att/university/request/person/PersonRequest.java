package com.att.university.request.person;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder(setterPrefix = "with", toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
public abstract class PersonRequest {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
}
