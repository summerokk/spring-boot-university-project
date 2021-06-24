package com.att.mapper.student;

import com.att.entity.Group;
import com.att.entity.Student;
import com.att.request.person.student.StudentUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentUpdateRequestMapper {
    @Mapping(target = "withPassword", source = "password")
    @Mapping(target = "withGroup", source = "group")
    @Mapping(target = "withId", source = "request.id")
    @Mapping(target = "withFirstName", source = "request.firstName")
    @Mapping(target = "withLastName", source = "request.lastName")
    @Mapping(target = "withEmail", source = "request.email")
    Student convertToEntity(StudentUpdateRequest request, Group group, String password);
}
