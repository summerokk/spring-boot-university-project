package com.att.mapper.group;

import com.att.entity.Faculty;
import com.att.entity.Group;
import com.att.request.group.GroupAddRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GroupAddRequestMapper {
    @Mapping(source = "faculty", target = "faculty")
    @Mapping(source = "request.name", target = "name")
    @Mapping(target = "id", ignore = true)
    Group convertToEntity(GroupAddRequest request, Faculty faculty);
}
