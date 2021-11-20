package com.att.mapper.faculty;

import com.att.entity.Faculty;
import com.att.request.faculty.FacultyUpdateRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FacultyUpdateRequestMapper {
    Faculty convertToEntity(FacultyUpdateRequest updateRequest);
}
