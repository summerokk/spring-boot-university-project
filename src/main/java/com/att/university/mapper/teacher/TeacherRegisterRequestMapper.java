package com.att.university.mapper.teacher;

import com.att.university.entity.AcademicRank;
import com.att.university.entity.ScienceDegree;
import com.att.university.entity.Teacher;
import com.att.university.request.person.teacher.TeacherRegisterRequest;
import org.springframework.stereotype.Component;

@Component
public class TeacherRegisterRequestMapper {
    public Teacher convertToEntity(TeacherRegisterRequest registerRequest, String password, AcademicRank academicRank,
                                   ScienceDegree scienceDegree) {
        return Teacher.builder()
                .withFirstName(registerRequest.getFirstName())
                .withLastName(registerRequest.getLastName())
                .withEmail(registerRequest.getEmail())
                .withPassword(password)
                .withAcademicRank(academicRank)
                .withScienceDegree(scienceDegree)
                .withLinkedin(registerRequest.getLinkedin())
                .build();
    }
}
