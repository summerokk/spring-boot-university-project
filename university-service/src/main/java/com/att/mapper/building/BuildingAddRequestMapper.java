package com.att.mapper.building;

import com.att.entity.Building;
import com.att.request.building.BuildingAddRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BuildingAddRequestMapper {
    Building convertToEntity(BuildingAddRequest addRequest);
}
