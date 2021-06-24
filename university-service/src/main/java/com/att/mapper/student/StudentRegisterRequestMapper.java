package com.att.mapper.student;

import com.att.entity.Student;
import com.att.request.person.student.StudentRegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentRegisterRequestMapper {
    @Mapping(target = "withPassword", source = "password")
    @Mapping(target = "withFirstName", source = "request.firstName")
    @Mapping(target = "withLastName", source = "request.lastName")
    @Mapping(target = "withEmail", source = "request.email")
    Student convertToEntity(StudentRegisterRequest request, String password);
}
