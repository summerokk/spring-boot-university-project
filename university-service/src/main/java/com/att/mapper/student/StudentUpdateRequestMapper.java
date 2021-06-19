package com.att.mapper.student;

import com.att.entity.Group;
import com.att.entity.Student;
import com.att.request.person.student.StudentUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentUpdateRequestMapper {
    @Mapping(target = "password", source = "password")
    @Mapping(target = "group", source = "group")
    @Mapping(target = "id", source = "request.id")
    Student convertToEntity(StudentUpdateRequest request, Group group, String password);
}
