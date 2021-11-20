package com.att.mapper.building;

import com.att.entity.Building;
import com.att.request.building.BuildingUpdateRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BuildingUpdateRequestMapper {
    Building convertToEntity(BuildingUpdateRequest updateRequest);
}
