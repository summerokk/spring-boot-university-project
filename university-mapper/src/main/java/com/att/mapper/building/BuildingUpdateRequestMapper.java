package com.att.mapper.building;

import com.att.request.building.BuildingUpdateRequest;
import com.att.entity.Building;
import org.springframework.stereotype.Component;

@Component
public class BuildingUpdateRequestMapper {
    public Building convertToEntity(BuildingUpdateRequest updateRequest) {
        return new Building(updateRequest.getId(), updateRequest.getAddress());
    }
}
