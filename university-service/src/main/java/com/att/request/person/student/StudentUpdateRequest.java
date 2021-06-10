package com.att.request.person.student;

import com.att.request.person.PersonRequest;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class StudentUpdateRequest extends PersonRequest {
    @NotNull
    private Integer id;

    @NotNull
    private Integer groupId;

    @Builder(setterPrefix = "with")
    public StudentUpdateRequest(String firstName, String lastName, String email, Integer id, Integer groupId) {
        super(firstName, lastName, email);

        this.id = id;
        this.groupId = groupId;
    }
}
