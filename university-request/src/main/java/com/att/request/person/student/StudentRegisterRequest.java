package com.att.request.person.student;

import com.att.request.person.PersonRequest;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class StudentRegisterRequest extends PersonRequest {
    private String password;
    private String passwordConfirm;

    @Builder(setterPrefix = "with")
    public StudentRegisterRequest(String firstName, String lastName, String email, String password,
                                  String passwordConfirm) {
        super(firstName, lastName, email);

        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }
}
