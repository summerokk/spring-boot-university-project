package com.att.request.person.teacher;

import com.att.request.person.PersonRequest;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TeacherRegisterRequest extends PersonRequest {
    private String password;
    private String passwordConfirm;
    private String linkedin;
    private Integer academicRankId;
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
