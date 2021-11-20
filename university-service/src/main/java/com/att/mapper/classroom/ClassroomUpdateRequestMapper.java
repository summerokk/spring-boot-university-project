package com.att.mapper.classroom;

import com.att.entity.Building;
import com.att.entity.Classroom;
import com.att.request.classroom.ClassroomUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClassroomUpdateRequestMapper {
    @Mapping(target = "id", source = "request.id")
    @Mapping(target = "building", source = "building")
    Classroom convertToEntity(ClassroomUpdateRequest request, Building building);
}
