package com.att.request.person.student;

import com.att.request.person.PersonRequest;
import com.att.validator.FieldsValueMatch;
import com.att.validator.ValidPassword;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "password",
                fieldMatch = "passwordConfirm",
                message = "{password.match}"
        )
})
@NoArgsConstructor
@Setter
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class StudentRegisterRequest extends PersonRequest {
    @ValidPassword
    private String password;

    @ValidPassword
    private String passwordConfirm;

    @Builder(setterPrefix = "with")
    public StudentRegisterRequest(String firstName, String lastName, String email, String password,
                                  String passwordConfirm) {
        super(firstName, lastName, email);

        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }
}
