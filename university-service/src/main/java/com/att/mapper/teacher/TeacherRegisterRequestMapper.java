package com.att.mapper.teacher;

import com.att.request.person.teacher.TeacherRegisterRequest;
import com.att.entity.AcademicRank;
import com.att.entity.ScienceDegree;
import com.att.entity.Teacher;
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
