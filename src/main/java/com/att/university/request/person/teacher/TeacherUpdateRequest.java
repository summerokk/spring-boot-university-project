package com.att.university.request.person.teacher;

import com.att.university.request.person.PersonRequest;
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
public class TeacherUpdateRequest extends PersonRequest {
    private Integer id;
    private String linkedin;
    private Integer academicRankId;
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
