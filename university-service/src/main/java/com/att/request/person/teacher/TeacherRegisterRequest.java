package com.att.request.person.teacher;

import com.att.request.person.PersonRequest;
import com.att.validator.FieldsValueMatch;
import com.att.validator.ValidPassword;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "password",
                fieldMatch = "passwordConfirm",
                message = "{password.match}"
        )
})
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TeacherRegisterRequest extends PersonRequest {
    @ValidPassword
    private String password;

    @ValidPassword
    private String passwordConfirm;

    @Pattern(regexp="^http(s)?://linkedin.com/.+", message="{linkedin.field}")
    private String linkedin;

    @NotNull
    private Integer academicRankId;

    @NotNull
    private Integer scienceDegreeId;

    @Builder(setterPrefix = "with")
    public TeacherRegisterRequest(String firstName, String lastName, String email, String password,
                                  String passwordConfirm, String linkedin, Integer academicRankId,
                                  Integer scienceDegreeId) {
        super(firstName, lastName, email);

        this.linkedin = linkedin;
        this.academicRankId = academicRankId;
        this.scienceDegreeId = scienceDegreeId;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }
}
