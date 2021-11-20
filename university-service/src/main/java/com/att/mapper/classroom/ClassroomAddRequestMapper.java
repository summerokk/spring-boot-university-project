package com.att.mapper.classroom;

import com.att.entity.Building;
import com.att.entity.Classroom;
import com.att.request.classroom.ClassroomAddRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClassroomAddRequestMapper {
    @Mapping(target = "building", source = "building")
    @Mapping(target = "id", ignore = true)
    Classroom convertToEntity(ClassroomAddRequest addRequest, Building building);
}
