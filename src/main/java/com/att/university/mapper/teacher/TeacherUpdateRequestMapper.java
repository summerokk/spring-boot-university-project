package com.att.university.mapper.teacher;

import com.att.university.entity.AcademicRank;
import com.att.university.entity.ScienceDegree;
import com.att.university.entity.Teacher;
import com.att.university.request.person.teacher.TeacherUpdateRequest;
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
