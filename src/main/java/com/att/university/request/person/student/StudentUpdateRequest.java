package com.att.university.request.person.student;

import com.att.university.request.person.PersonRequest;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class StudentUpdateRequest extends PersonRequest {
    private Integer id;
    private Integer groupId;

    @Builder(setterPrefix = "with")
    public StudentUpdateRequest(String firstName, String lastName, String email, String password,
                                  String passwordConfirm, Integer id, Integer groupId) {
        super(firstName, lastName, email, password, passwordConfirm);

        this.id = id;
        this.groupId = groupId;
    }
}
