package com.att.mapper.faculty;

import com.att.entity.Faculty;
import com.att.request.faculty.FacultyAddRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FacultyAddRequestMapper {
    Faculty convertToEntity(FacultyAddRequest addRequest);
}
