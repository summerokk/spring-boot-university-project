package com.att.university.request.person.student;

import com.att.university.request.person.PersonRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder(setterPrefix = "with")
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class StudentUpdateRequest extends PersonRequest {
    private final Integer id;
    private final Integer groupId;
}
