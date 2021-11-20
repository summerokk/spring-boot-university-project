package com.att.mapper.teacher;

import com.att.entity.AcademicRank;
import com.att.entity.ScienceDegree;
import com.att.entity.Teacher;
import com.att.request.person.teacher.TeacherRegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TeacherRegisterRequestMapper {
    @Mapping(target = "withPassword", source = "password")
    @Mapping(target = "withFirstName", source = "request.firstName")
    @Mapping(target = "withLastName", source = "request.lastName")
    @Mapping(target = "withEmail", source = "request.email")
    @Mapping(target = "withAcademicRank", source = "academicRank")
    @Mapping(target = "withScienceDegree", source = "scienceDegree")
    @Mapping(target = "withLinkedin", source = "request.linkedin")
    Teacher convertToEntity(TeacherRegisterRequest request, String password, AcademicRank academicRank,
                                   ScienceDegree scienceDegree);
}
