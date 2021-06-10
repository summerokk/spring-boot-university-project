package com.att.request.person.teacher;

import com.att.request.person.PersonRequest;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TeacherUpdateRequest extends PersonRequest {
    @NotNull
    private Integer id;

    @Pattern(regexp="^http(s)?://linkedin.com/.+", message="{linkedin.field}")
    private String linkedin;

    @NotNull
    private Integer academicRankId;

    @NotNull
    private Integer scienceDegreeId;

    @Builder(setterPrefix = "with")
    public TeacherUpdateRequest(Integer id, String linkedin, Integer academicRankId, Integer scienceDegreeId,
                                String firstName, String lastName, String email) {
        super(firstName, lastName, email);

        this.id = id;
        this.linkedin = linkedin;
        this.academicRankId = academicRankId;
        this.scienceDegreeId = scienceDegreeId;
    }
}
