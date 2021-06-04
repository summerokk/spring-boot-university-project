package com.att.mapper.teacher;

import com.att.request.person.teacher.TeacherUpdateRequest;
import com.att.entity.AcademicRank;
import com.att.entity.ScienceDegree;
import com.att.entity.Teacher;
import org.springframework.stereotype.Component;

@Component
public class TeacherUpdateRequestMapper {
    public Teacher convertToEntity(TeacherUpdateRequest updateRequest, AcademicRank academicRank,
                                   ScienceDegree scienceDegree) {
        return Teacher.builder()
                .withId(updateRequest.getId())
                .withFirstName(updateRequest.getFirstName())
                .withLastName(updateRequest.getLastName())
                .withEmail(updateRequest.getEmail())
                .withAcademicRank(academicRank)
                .withScienceDegree(scienceDegree)
                .withLinkedin(updateRequest.getLinkedin())
                .build();
    }
}
