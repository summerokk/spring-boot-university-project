package com.att.mapper.group;

import com.att.entity.Faculty;
import com.att.entity.Group;
import com.att.request.group.GroupUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GroupUpdateRequestMapper {
    @Mapping(source = "faculty", target = "faculty")
    @Mapping(source = "request.name", target = "name")
    @Mapping(source = "request.id", target = "id")
    Group convertToEntity(GroupUpdateRequest request, Faculty faculty);
}
