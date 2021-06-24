package com.att.mapper.group;

import com.att.entity.Faculty;
import com.att.entity.Group;
import com.att.request.group.GroupAddRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION)
public interface GroupAddRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "faculty", target = "faculty")
    @Mapping(source = "request.name", target = "name")
    Group convertToEntity(GroupAddRequest request, Faculty faculty);
}
