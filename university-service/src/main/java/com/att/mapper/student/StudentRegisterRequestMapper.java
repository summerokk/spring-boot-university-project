package com.att.mapper.student;

import com.att.entity.Student;
import com.att.request.person.student.StudentRegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentRegisterRequestMapper {
    @Mapping(target = "password", source = "password")
    Student convertToEntity(StudentRegisterRequest request, String password);
}
